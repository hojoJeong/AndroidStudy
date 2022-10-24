package com.ssafy.motionsensor

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class GravitySensor : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var gravitySensor: Sensor

    var total = 0.0
    private lateinit var tvX: TextView
    private lateinit var tvY: TextView
    private lateinit var tvZ: TextView
    private lateinit var tvGravTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gravity_sensor)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        tvX = findViewById(R.id.tvX)
        tvY = findViewById(R.id.tvY)
        tvZ = findViewById(R.id.tvZ)
        tvGravTotal = findViewById(R.id.tvGravTotal)
    }
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor == gravitySensor) {
            total = Math.sqrt(
                Math.pow(event.values[0].toDouble(), 2.0) + Math.pow(
                    event.values[1].toDouble(), 2.0
                ) + Math.pow(event.values[2].toDouble(), 2.0)
            )
            tvX.text = "X axis : ${String.format("%.2f", event.values[0])}"
            tvY.text = "Y axis : ${String.format("%.2f", event.values[1])}"
            tvZ.text = "Z axis :  ${String.format("%.2f", event.values[2])}"
            tvGravTotal.text = "Total Gravity : ${java.lang.String.format("%.2f", total)} m/s\u00B2"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}