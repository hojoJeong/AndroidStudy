package com.ssafy.palette_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class StartActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)


        findViewById<Button>(R.id.button_1).setOnClickListener{
            var intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_2).setOnClickListener{
            var intent = Intent(this, ImageButtonActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_3).setOnClickListener{
            var intent = Intent(this, ChipActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_4).setOnClickListener{
            var intent = Intent(this, CheckBoxActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_5).setOnClickListener{
            var intent = Intent(this, RadioActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_6).setOnClickListener{
            var intent = Intent(this, ToggleActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_7).setOnClickListener{
            var intent = Intent(this, SwitchActivity::class.java);
            startActivity(intent)
        }



    }

}