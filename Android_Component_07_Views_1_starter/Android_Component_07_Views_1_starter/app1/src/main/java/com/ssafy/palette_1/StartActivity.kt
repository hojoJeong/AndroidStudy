package com.ssafy.palette_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.palette_1.progressbar.ProgressBarActivity
import com.ssafy.palette_1.progressbar.ProgressBarActivity2


class StartActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        findViewById<Button>(R.id.button_1).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_2).setOnClickListener{
            val intent = Intent(this, VideoViewActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_3).setOnClickListener{
            val intent = Intent(this, CalendarViewActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_4).setOnClickListener{
            val intent = Intent(this, ProgressBarActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_5).setOnClickListener{
            val intent = Intent(this, ProgressBarActivity2::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_6).setOnClickListener{
            val intent = Intent(this, SeekBarActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_7).setOnClickListener{
            val intent = Intent(this, RatingBarActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_8).setOnClickListener{
            val intent = Intent(this, SearchViewActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_9).setOnClickListener{
            val intent = Intent(this, TextureViewActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_10).setOnClickListener{

        }
    }

}