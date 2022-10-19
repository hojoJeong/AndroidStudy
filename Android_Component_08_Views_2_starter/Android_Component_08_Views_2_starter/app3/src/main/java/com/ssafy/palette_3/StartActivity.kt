package com.ssafy.palette_3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class StartActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)


        findViewById<Button>(R.id.button_1).setOnClickListener{
            var intent = Intent(this, AutoCompleteTextViewActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_2).setOnClickListener{
            var intent = Intent(this, MultiAutoActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_3).setOnClickListener{
            var intent = Intent(this, TextInputLayoutActivity::class.java);
            startActivity(intent)
        }
        findViewById<Button>(R.id.button_4).setOnClickListener{
            var intent = Intent(this, ScrollViewActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_5).setOnClickListener{
            var intent = Intent(this, HorizontalScrollViewActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_6).setOnClickListener{
            var intent = Intent(this, NestedScrollViewActivity::class.java);
            startActivity(intent)
        }


        findViewById<Button>(R.id.button_8).setOnClickListener{
            var intent = Intent(this, SpinnerActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_9).setOnClickListener{
            var intent = Intent(this, FloatingActivity::class.java);
            startActivity(intent)
        }


    }

}