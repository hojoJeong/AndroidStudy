package com.ssafy.motionsensor

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.motionsensor.databinding.ActivityGyroscopeBinding

class GyroScope : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var gyroSensor: Sensor

    private var timestamp = 0.0
    private var dt = 0.0

    private val RAD2DGR = 180 / Math.PI
    private val NS2S = 1.0f / 1000000000.0f
    private var pitch = 0.0
    private var roll = 0.0
    private var yaw = 0.0

    private lateinit var binding:ActivityGyroscopeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGyroscopeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    }
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent) {
        dt = (event.timestamp - timestamp) * NS2S;
        timestamp = event.timestamp.toDouble()

        val gyroX = event.values[0]
        val gyroY = event.values[1]
        val gyroZ = event.values[2]

        binding.tvX.text =  "X axis : ${String.format("%.2f", gyroX)}"
        binding.tvY.text = "Y axis :  ${String.format("%.2f", gyroY)}"
        binding.tvZ.text = "Z axis :  ${String.format("%.2f", gyroZ)}"
        if (dt - timestamp*NS2S != 0.0) {

            pitch += gyroY * dt;
            roll += gyroX * dt;
            yaw += gyroZ * dt;

            binding.tvRoll.text ="[Roll]:  ${String.format("%.1f", roll*RAD2DGR)}"
            binding.tvPitch.text ="[Pitch]:  ${String.format("%.1f", pitch*RAD2DGR)}"
            binding.tvYaw.text ="[Yaw]:  ${String.format("%.1f", yaw*RAD2DGR)}"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}