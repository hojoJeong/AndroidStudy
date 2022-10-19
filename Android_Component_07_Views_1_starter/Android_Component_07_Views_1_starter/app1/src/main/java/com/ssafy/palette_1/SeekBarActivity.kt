package com.ssafy.palette_1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.ssafy.palette_1.databinding.ActivitySeekBarBinding

@SuppressLint("SetTextI18n")
class SeekBarActivity : AppCompatActivity() {

    lateinit var binding : ActivitySeekBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeekBarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.progressTv.text = "onProgressChanged : $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                binding.progressTv.text = "onStartTrackingTouch : ${seekBar!!.progress}"
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                binding.progressTv.text = "onStopTrackingTouch : ${seekBar!!.progress}"
            }

        })


        binding.button.setOnClickListener {
            startActivity(Intent(this, RatingBarActivity::class.java))
        }
    }
}