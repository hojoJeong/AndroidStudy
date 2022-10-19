package com.ssafy.palette_1.progressbar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.palette_1.SeekBarActivity
import com.ssafy.palette_1.databinding.ActivityProgressBar2Binding

class ProgressBarActivity2 : AppCompatActivity() {

    private var i = 0
    private val handler = Handler(Looper.getMainLooper())
    lateinit var binding: ActivityProgressBar2Binding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBar2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressStartBtn.setOnClickListener {
            i = binding.progressBar.progress

            Thread {

                while (i < 100) {
                    // 진행 단계 100까지 +1 반복
                    i += 1
                    // Progress Bar 의 진행상황을 TextView 를 통해 보여주기
                    handler.post {

                            binding.progressBar.progress = i
                            binding.progressStateTv.text =
                                i.toString() + "/" + binding.progressBar.max

                    }
                    // Progress 진행상황을 천천히 보여주기
                    Thread.sleep(100)
                }
            }.start()
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, SeekBarActivity::class.java))
        }
    }
}