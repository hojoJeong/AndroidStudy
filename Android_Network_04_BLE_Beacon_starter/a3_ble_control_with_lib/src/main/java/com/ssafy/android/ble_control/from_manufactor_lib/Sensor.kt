/**************************************************************************************************
 * Filename:       Sensor.java
 *
 * Copyright (c) 2013 - 2014 Texas Instruments Incorporated
 *
 * All rights reserved not granted herein.
 * Limited License.
 *
 * Texas Instruments Incorporated grants a world-wide, royalty-free,
 * non-exclusive license under copyrights and patents it now or hereafter
 * owns or controls to make, have made, use, import, offer to sell and sell ("Utilize")
 * this software subject to the terms herein.  With respect to the foregoing patent
 * license, such license is granted  solely to the extent that any such patent is necessary
 * to Utilize the software alone.  The patent license shall not apply to any combinations which
 * include this software, other than combinations with devices manufactured by or for TI ('TI Devices').
 * No hardware patent is licensed hereunder.
 *
 * Redistributions must preserve existing copyright notices and reproduce this license (including the
 * above copyright notice and the disclaimer and (if applicable) source code license limitations below)
 * in the documentation and/or other materials provided with the distribution
 *
 * Redistribution and use in binary form, without modification, are permitted provided that the following
 * conditions are met:
 *
 * No reverse engineering, decompilation, or disassembly of this software is permitted with respect to any
 * software provided in binary form.
 * any redistribution and use are licensed by TI for use only with TI Devices.
 * Nothing shall obligate TI to provide you with source code for the software licensed and provided to you in object code.
 *
 * If software source code is provided to you, modification and redistribution of the source code are permitted
 * provided that the following conditions are met:
 *
 * any redistribution and use of the source code, including any resulting derivative works, are licensed by
 * TI for use only with TI Devices.
 * any redistribution and use of any object code compiled from the source code and any resulting derivative
 * works, are licensed by TI for use only with TI Devices.
 *
 * Neither the name of Texas Instruments Incorporated nor the names of its suppliers may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * DISCLAIMER.
 *
 * THIS SOFTWARE IS PROVIDED BY TI AND TI'S LICENSORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL TI AND TI'S LICENSORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */
package com.ssafy.android.ble_control.from_manufactor_lib

import android.bluetooth.BluetoothGattCharacteristic
import java.util.*

//import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT8;
/**
 * This enum encapsulates the differences amongst the sensors. The differences include UUID values and how to interpret the
 * characteristic-containing-measurement.
 */
enum class Sensor {
    IR_TEMPERATURE(
        SensorTagGatt.UUID_IR_TEMPERATURE_SERV,
        SensorTagGatt.UUID_IRT_DATA,
        SensorTagGatt.UUID_IRT_CONF
    ) {
        override fun convert(value: ByteArray): Point3D {

            /*
			 * The IR Temperature sensor produces two measurements; Object ( AKA target or IR) Temperature, and Ambient ( AKA die ) temperature.
			 * Both need some conversion, and Object temperature is dependent on Ambient temperature.
			 * They are stored as [ObjLSB, ObjMSB, AmbLSB, AmbMSB] (4 bytes) Which means we need to shift the bytes around to get the correct values.
			 */
            val ambient = extractAmbientTemperature(value)
            val target = extractTargetTemperature(value, ambient)
            val targetNewSensor = extractTargetTemperatureTMP007(value)
            return Point3D(ambient, target, targetNewSensor)
        }

        private fun extractAmbientTemperature(v: ByteArray): Double {
            val offset = 2
            return shortUnsignedAtOffset(v, offset) / 128.0
        }

        private fun extractTargetTemperature(v: ByteArray, ambient: Double): Double {
            val twoByteValue = shortSignedAtOffset(v, 0)
            var Vobj2 = twoByteValue.toDouble()
            Vobj2 *= 0.00000015625
            val Tdie = ambient + 273.15
            val S0 = 5.593E-14 // Calibration factor
            val a1 = 1.75E-3
            val a2 = -1.678E-5
            val b0 = -2.94E-5
            val b1 = -5.7E-7
            val b2 = 4.63E-9
            val c2 = 13.4
            val Tref = 298.15
            val S = S0 * (1 + a1 * (Tdie - Tref) + a2 * Math.pow(Tdie - Tref, 2.0))
            val Vos = b0 + b1 * (Tdie - Tref) + b2 * Math.pow(Tdie - Tref, 2.0)
            val fObj = Vobj2 - Vos + c2 * Math.pow(Vobj2 - Vos, 2.0)
            val tObj = Math.pow(Math.pow(Tdie, 4.0) + fObj / S, .25)
            return tObj - 273.15
        }

        private fun extractTargetTemperatureTMP007(v: ByteArray): Double {
            val offset = 0
            return shortUnsignedAtOffset(v, offset) / 128.0
        }
    },
    MOVEMENT_ACC(
        SensorTagGatt.UUID_MOVEMENT_SERV,
        SensorTagGatt.UUID_MOV_DATA,
        SensorTagGatt.UUID_MOV_CONF,
        3.toByte()
    ) {
        override fun convert(value: ByteArray): Point3D {
            // Range 8G
            val SCALE = 4096.0.toFloat()
            val x: Int = (value[7].toInt() shl 8) + value[6]
            val y: Int = (value[9].toInt() shl 8) + value[8]
            val z: Int = (value[11].toInt() shl 8) + value[10]
            return Point3D(
                (x / SCALE * -1).toDouble(),
                (y / SCALE).toDouble(),
                (z / SCALE * -1).toDouble()
            )
        }
    },
    MOVEMENT_GYRO(
        SensorTagGatt.UUID_MOVEMENT_SERV,
        SensorTagGatt.UUID_MOV_DATA,
        SensorTagGatt.UUID_MOV_CONF,
        3.toByte()
    ) {
        override fun convert(value: ByteArray): Point3D {
            val SCALE = 128.0.toFloat()
            val x: Int = (value[1].toInt() shl 8) + value[0]
            val y: Int = (value[3].toInt() shl 8) + value[2]
            val z: Int = (value[5].toInt() shl 8) + value[4]
            return Point3D((x / SCALE).toDouble(), (y / SCALE).toDouble(), (z / SCALE).toDouble())
        }
    },
    MOVEMENT_MAG(
        SensorTagGatt.UUID_MOVEMENT_SERV,
        SensorTagGatt.UUID_MOV_DATA,
        SensorTagGatt.UUID_MOV_CONF,
        3.toByte()
    ) {
        override fun convert(value: ByteArray): Point3D {
            val SCALE = (32768 / 4912).toFloat()
            return if (value.size >= 18) {
                val x: Int = (value[13].toInt() shl 8) + value[12]
                val y: Int = (value[15].toInt() shl 8) + value[14]
                val z: Int = (value[17].toInt() shl 8) + value[16]
                Point3D((x / SCALE).toDouble(), (y / SCALE).toDouble(), (z / SCALE).toDouble())
            } else Point3D(0.0, 0.0, 0.0)
        }
    },
    ACCELEROMETER(
        SensorTagGatt.UUID_ACCELEROMETER_SERV,
        SensorTagGatt.UUID_ACC_DATA,
        SensorTagGatt.UUID_ACC_CONF,
        3.toByte()
    ) {
        override fun convert(value: ByteArray): Point3D {
            /*
			 * The accelerometer has the range [-2g, 2g] with unit (1/64)g.
			 * To convert from unit (1/64)g to unit g we divide by 64.
			 * (g = 9.81 m/s^2)
			 * The z value is multiplied with -1 to coincide with how we have arbitrarily defined the positive y direction. (illustrated by the apps accelerometer
			 * image)
			 */
//			DeviceActivity da = DeviceActivity.getInstance();
//
//			if (da.isSensorTag2()) {
//				// Range 8G
//				final float SCALE = (float) 4096.0;
//
//				int x = (value[0]<<8) + value[1];
//				int y = (value[2]<<8) + value[3];
//				int z = (value[4]<<8) + value[5];
//				return new Point3D(x / SCALE, y / SCALE, z / SCALE);
//			} else {
            val v: Point3D
            val x = value[0].toInt()
            val y = value[1].toInt()
            val z = value[2].toInt() * -1

//				if (da.firmwareRevision().contains("1.5"))
//				{
            // Range 8G
            val SCALE = 64.0.toFloat()
            v = Point3D((x / SCALE).toDouble(), (y / SCALE).toDouble(), (z / SCALE).toDouble())
            //				} else {
//					// Range 2G
//					final float SCALE = (float) 16.0;
//					v = new Point3D(x / SCALE, y / SCALE, z / SCALE);
//				}
            return v
            //			}
        }
    },
    HUMIDITY(
        SensorTagGatt.UUID_HUMIDITY_SERV,
        SensorTagGatt.UUID_HUM_DATA,
        SensorTagGatt.UUID_HUM_CONF
    ) {
        override fun convert(value: ByteArray): Point3D {
            var a = shortUnsignedAtOffset(value, 2)
            // bits [1..0] are status bits and need to be cleared according
            // to the user guide, but the iOS code doesn't bother. It should
            // have minimal impact.
            a = a - a % 4
            return Point3D((-6f + 125f * (a / 65535f)).toDouble(), 0.0, 0.0)
        }
    },
    HUMIDITY2(
        SensorTagGatt.UUID_HUMIDITY_SERV,
        SensorTagGatt.UUID_HUM_DATA,
        SensorTagGatt.UUID_HUM_CONF
    ) {
        override fun convert(value: ByteArray): Point3D {
            val a = shortUnsignedAtOffset(value, 2)
            return Point3D((100f * (a / 65535f)).toDouble(), 0.0, 0.0)
        }
    },
    MAGNETOMETER(
        SensorTagGatt.UUID_MAGNETOMETER_SERV,
        SensorTagGatt.UUID_MAG_DATA,
        SensorTagGatt.UUID_MAG_CONF
    ) {
        override fun convert(value: ByteArray): Point3D {
            val mcal = MagnetometerCalibrationCoefficients.INSTANCE.`val`
            // Multiply x and y with -1 so that the values correspond with the image in the app
            val x = shortSignedAtOffset(value, 0) * (2000f / 65536f) * -1
            val y = shortSignedAtOffset(value, 2) * (2000f / 65536f) * -1
            val z = shortSignedAtOffset(value, 4) * (2000f / 65536f)
            return Point3D(x - mcal!!.x, y - mcal.y, z - mcal.z)
        }
    },
    LUXOMETER(
        SensorTagGatt.UUID_OPTICAL_SERV,
        SensorTagGatt.UUID_OPT_DATA,
        SensorTagGatt.UUID_OPT_CONF
    ) {
        override fun convert(value: ByteArray): Point3D {
            val mantissa: Int
            val exponent: Int
            val sfloat = shortUnsignedAtOffset(value, 0)
            mantissa = sfloat and 0x0FFF
            exponent = sfloat shr 12 and 0xFF
            val output: Double
            val magnitude = Math.pow(2.0, exponent.toDouble())
            output = mantissa * magnitude
            return Point3D(output / 100.0f, 0.0, 0.0)
        }
    },
    GYROSCOPE(
        SensorTagGatt.UUID_GYR_SERV,
        SensorTagGatt.UUID_GYR_DATA,
        SensorTagGatt.UUID_GYR_CONF,
        7.toByte()
    ) {
        override fun convert(value: ByteArray): Point3D {
            val y = shortSignedAtOffset(value, 0) * (500f / 65536f) * -1
            val x = shortSignedAtOffset(value, 2) * (500f / 65536f)
            val z = shortSignedAtOffset(value, 4) * (500f / 65536f)
            return Point3D(x.toDouble(), y.toDouble(), z.toDouble())
        }
    },
    BAROMETER(
        SensorTagGatt.UUID_BAROMETER_SERV,
        SensorTagGatt.UUID_BAR_DATA,
        SensorTagGatt.UUID_BAR_CONF
    ) {
        override fun convert(value: ByteArray): Point3D {

//			if (DeviceActivity.getInstance().isSensorTag2()) {
//                if (value.length > 4) {
//                    Integer val = twentyFourBitUnsignedAtOffset(value, 2);
//                    return new Point3D((double) val / 100.0, 0, 0);
//                }
//				else {
//                    int mantissa;
//                    int exponent;
//                    Integer sfloat = shortUnsignedAtOffset(value, 2);
//
//                    mantissa = sfloat & 0x0FFF;
//                    exponent = (sfloat >> 12) & 0xFF;
//
//                    double output;
//                    double magnitude = pow(2.0f, exponent);
//                    output = (mantissa * magnitude);
//                    return new Point3D(output / 100.0f, 0, 0);
//                }
//
//
//				/*
//				Integer val = shortUnsignedAtOffset(value, 2);
//				Log.d("TEST","Value " + value[3] + " " + value[2] + " Val: " + val);
//
//				return new Point3D((double)val / 100.0,0,0);
//				*/
//			} else {
            val barometerCalibrationCoefficients =
                BarometerCalibrationCoefficients.INSTANCE.barometerCalibrationCoefficients
                    ?: // Log.w("Sensor", "Data notification arrived for barometer before it was calibrated.");
                    return Point3D(0.0, 0.0, 0.0)
            val c: IntArray // Calibration coefficients
            val t_r: Int // Temperature raw value from sensor
            val p_r: Int // Pressure raw value from sensor
            val S: Double // Interim value in calculation
            val O: Double // Interim value in calculation
            val p_a: Double // Pressure actual value in unit Pascal.
            c = IntArray(barometerCalibrationCoefficients.size)
            for (i in barometerCalibrationCoefficients.indices) {
                c[i] = barometerCalibrationCoefficients[i]!!
            }
            t_r = shortSignedAtOffset(value, 0)
            p_r = shortUnsignedAtOffset(value, 2)
            S = c[2] + c[3] * t_r / Math.pow(2.0, 17.0) + c[4] * t_r / Math.pow(
                2.0,
                15.0
            ) * t_r / Math.pow(2.0, 19.0)
            O = c[5] * Math.pow(2.0, 14.0) + c[6] * t_r / Math.pow(
                2.0,
                3.0
            ) + c[7] * t_r / Math.pow(2.0, 15.0) * t_r / Math.pow(2.0, 4.0)
            p_a = (S * p_r + O) / Math.pow(2.0, 14.0)
            return Point3D(p_a, 0.0, 0.0)
//			}
        }
    };

    fun onCharacteristicChanged(c: BluetoothGattCharacteristic?) {
        throw UnsupportedOperationException("Error: the individual enum classes are supposed to override this method.")
    }

    open fun convert(value: ByteArray): Point3D {
        throw UnsupportedOperationException("Error: the individual enum classes are supposed to override this method.")
    }

    val service: UUID?
    val data: UUID?
    val config: UUID?

    /**
     * @return the code which, when written to the configuration characteristic, turns on the sensor.
     */
    var enableSensorCode // See getEnableSensorCode for explanation.
            : Byte
        private set

    /**
     * Constructor called by the Gyroscope and Accelerometer because it more than a boolean enable
     * code.
     */
    constructor(service: UUID?, data: UUID?, config: UUID?, enableCode: Byte) {
        this.service = service
        this.data = data
        this.config = config
        enableSensorCode = enableCode
    }

    /**
     * Constructor called by all the sensors except Gyroscope
     */
    constructor(service: UUID?, data: UUID?, config: UUID?) {
        this.service = service
        this.data = data
        this.config = config
        enableSensorCode =
            ENABLE_SENSOR_CODE // This is the sensor enable code for all sensors except the gyroscope
    }

    companion object {
        /**
         * Gyroscope, Magnetometer, Barometer, IR temperature all store 16 bit two's complement values as LSB MSB, which cannot be directly parsed
         * as getIntValue(FORMAT_SINT16, offset) because the bytes are stored as little-endian.
         *
         * This function extracts these 16 bit two's complement values.
         */
        private fun shortSignedAtOffset(c: ByteArray, offset: Int): Int {
            val lowerByte = c[offset].toInt() and 0xFF
            val upperByte = c[offset + 1].toInt() // // Interpret MSB as signed
            return (upperByte shl 8) + lowerByte
        }

        private fun shortUnsignedAtOffset(c: ByteArray, offset: Int): Int {
            val lowerByte = c[offset].toInt() and 0xFF
            val upperByte = c[offset + 1].toInt() and 0xFF
            return (upperByte shl 8) + lowerByte
        }

        private fun twentyFourBitUnsignedAtOffset(c: ByteArray, offset: Int): Int {
            val lowerByte = c[offset].toInt() and 0xFF
            val mediumByte = c[offset + 1].toInt() and 0xFF
            val upperByte = c[offset + 2].toInt() and 0xFF
            return (upperByte shl 16) + (mediumByte shl 8) + lowerByte
        }

        const val DISABLE_SENSOR_CODE: Byte = 0
        const val ENABLE_SENSOR_CODE: Byte = 1
        const val CALIBRATE_SENSOR_CODE: Byte = 2
        fun getFromDataUuid(uuid: UUID): Sensor {
            for (s in values()) {
                if (s.data == uuid) {
                    return s
                }
            }
            throw RuntimeException("unable to find UUID.")
        }

        val SENSOR_LIST = arrayOf(
            IR_TEMPERATURE,
            ACCELEROMETER,
            MAGNETOMETER,
            LUXOMETER,
            GYROSCOPE,
            HUMIDITY,
            BAROMETER
        )
    }
}