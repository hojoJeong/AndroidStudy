package com.ssafy.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.viewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var count = 0
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.countText.text = count.toString()

        //버튼 클릭시 카운트 증가
        binding.increaseButton.setOnClickListener {
            count++
            binding.countText.text = count.toString()
        }
    }

    companion object{
        private const val KEY_COUNT="count"
    }
}