package com.ssafy.palette_3

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.palette_3.databinding.ActivitySpinnerBinding

class SpinnerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpinnerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idolList = arrayOf("아이유 남자친구 정호조", "브레이브걸스", "방탄소년단", "SG워너비", "블랙핑크", "아이즈원", "샤이니", "트와이스", "ITZY", "오마이걸")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, idolList)

        binding = ActivitySpinnerBinding.inflate(layoutInflater).apply {
            mainSpinner.adapter = spinnerAdapter
        }

        setContentView(binding.root)
    }
}