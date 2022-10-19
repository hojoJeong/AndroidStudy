package com.ssafy.palette_3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.palette_3.databinding.ActivityHorizentalScrollViewBinding

class HorizontalScrollViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHorizentalScrollViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHorizentalScrollViewBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}