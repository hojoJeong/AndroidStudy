package com.ssafy.motionsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SensorList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_list)

        val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors = sm.getSensorList(Sensor.TYPE_ALL)

        var sensorlist="전체 내장 센서의 수 : "+sensors.size+"\n"
        for (sensor in sensors) {
            sensorlist +=
                "name: ${sensor.name}\n" +
                "power: ${sensor.power}\n"+
                "resolution: ${sensor.resolution}\n"+
                "range: ${sensor.maximumRange}\n\n"
        }

        val textView = findViewById<TextView>(R.id.textView)
        textView.movementMethod = ScrollingMovementMethod()
        textView.text = sensorlist
    }
}