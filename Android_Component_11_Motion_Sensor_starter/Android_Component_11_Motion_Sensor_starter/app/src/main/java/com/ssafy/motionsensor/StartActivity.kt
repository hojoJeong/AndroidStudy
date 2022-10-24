package com.ssafy.motionsensor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class StartActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)


        findViewById<Button>(R.id.button_1).setOnClickListener{
            var intent = Intent(this, SensorList::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_2).setOnClickListener{
            var intent = Intent(this, LightSensor::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_3).setOnClickListener{
            var intent = Intent(this, StepDetectorActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_4).setOnClickListener{
                var intent = Intent(this, AccelerometerSensor::class.java);
            startActivity(intent)

        }

        findViewById<Button>(R.id.button_5).setOnClickListener{
            var intent = Intent(this, GravitySensor::class.java);
            startActivity(intent)

        }

        findViewById<Button>(R.id.button_6).setOnClickListener{
            var intent = Intent(this, GyroScope::class.java);
            startActivity(intent)

        }

    }

}