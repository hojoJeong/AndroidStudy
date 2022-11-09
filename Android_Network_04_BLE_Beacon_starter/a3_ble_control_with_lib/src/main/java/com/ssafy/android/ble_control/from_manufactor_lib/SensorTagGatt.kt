/**************************************************************************************************
 * Filename:       SensorTagGatt.java
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

import java.util.*

object SensorTagGatt {
    val UUID_DEVICE_INFORMATION_SERV = UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb")
    val UUID_DEVINFO_FWREV = UUID.fromString("00002A26-0000-1000-8000-00805f9b34fb")
    val UUID_IR_TEMPERATURE_SERV = UUID.fromString("f000aa00-0451-4000-b000-000000000000")
    val UUID_IRT_DATA = UUID.fromString("f000aa01-0451-4000-b000-000000000000")
    val UUID_IRT_CONF = UUID.fromString("f000aa02-0451-4000-b000-000000000000")
    val UUID_IRT_PERI = UUID.fromString("f000aa03-0451-4000-b000-000000000000") //period

    // Period in tens of milliseconds
    val UUID_ACCELEROMETER_SERV = UUID.fromString("f000aa10-0451-4000-b000-000000000000")
    val UUID_ACC_DATA = UUID.fromString("f000aa11-0451-4000-b000-000000000000")
    val UUID_ACC_CONF = UUID.fromString("f000aa12-0451-4000-b000-000000000000")
    // 0: disable, 1: enable
    val UUID_ACC_PERI = UUID.fromString("f000aa13-0451-4000-b000-000000000000")

    // Period in tens of milliseconds
    val UUID_HUMIDITY_SERV = UUID.fromString("f000aa20-0451-4000-b000-000000000000")
    val UUID_HUM_DATA = UUID.fromString("f000aa21-0451-4000-b000-000000000000")
    val UUID_HUM_CONF = UUID.fromString("f000aa22-0451-4000-b000-000000000000")
    // 0: disable, 1: enable
    val UUID_HUM_PERI = UUID.fromString("f000aa23-0451-4000-b000-000000000000")

    // Period in tens of milliseconds
    val UUID_MAGNETOMETER_SERV = UUID.fromString("f000aa30-0451-4000-b000-000000000000")
    val UUID_MAG_DATA = UUID.fromString("f000aa31-0451-4000-b000-000000000000")
    val UUID_MAG_CONF = UUID.fromString("f000aa32-0451-4000-b000-000000000000")
    // 0: disable, 1: enable
    val UUID_MAG_PERI = UUID.fromString("f000aa33-0451-4000-b000-000000000000")

    // Period in tens of milliseconds
    val UUID_OPTICAL_SERV = UUID.fromString("f000aa70-0451-4000-b000-000000000000")
    val UUID_OPT_DATA = UUID.fromString("f000aa71-0451-4000-b000-000000000000")
    val UUID_OPT_CONF = UUID.fromString("f000aa72-0451-4000-b000-000000000000")
    // 0: disable, 1: enable
    val UUID_OPT_PERI = UUID.fromString("f000aa73-0451-4000-b000-000000000000")

    // Period in tens of milliseconds
    val UUID_BAROMETER_SERV = UUID.fromString("f000aa40-0451-4000-b000-000000000000")
    val UUID_BAR_DATA = UUID.fromString("f000aa41-0451-4000-b000-000000000000")
    val UUID_BAR_CONF = UUID.fromString("f000aa42-0451-4000-b000-000000000000")
    // 0: disable, 1: enable
    val UUID_BAR_CALI = UUID.fromString("f000aa43-0451-4000-b000-000000000000")
    // Calibration characteristic
    val UUID_BAR_PERI = UUID.fromString("f000aa44-0451-4000-b000-000000000000")

    // Period in tens of milliseconds
    val UUID_GYR_SERV = UUID.fromString("f000aa50-0451-4000-b000-000000000000")
    val UUID_GYR_DATA = UUID.fromString("f000aa51-0451-4000-b000-000000000000")
    val UUID_GYR_CONF = UUID.fromString("f000aa52-0451-4000-b000-000000000000")

    // 0: disable, bit 0: enable x, bit 1: enable y, bit 2: enable z
    val UUID_GYR_PERI = UUID.fromString("f000aa53-0451-4000-b000-000000000000")

    // Period in tens of milliseconds
    val UUID_MOVEMENT_SERV = UUID.fromString("f000aa80-0451-4000-b000-000000000000")
    val UUID_MOV_DATA = UUID.fromString("f000aa81-0451-4000-b000-000000000000")
    val UUID_MOV_CONF = UUID.fromString("f000aa82-0451-4000-b000-000000000000")

    // 0: disable, bit 0: enable x, bit 1: enable y, bit 2: enable z
    val UUID_MOV_PERI = UUID.fromString("f000aa83-0451-4000-b000-000000000000")

    // Period in tens of milliseconds
    val UUID_TST_SERV = UUID.fromString("f000aa64-0451-4000-b000-000000000000")
    val UUID_TST_DATA = UUID.fromString("f000aa65-0451-4000-b000-000000000000")

    // Test result
    val UUID_KEY_SERV = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")
    val UUID_KEY_DATA = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
}