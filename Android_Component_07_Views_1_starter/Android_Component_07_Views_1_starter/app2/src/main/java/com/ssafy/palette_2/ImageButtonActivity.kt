package com.ssafy.palette_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.palette_2.databinding.ActivityImageButtonBinding

class ImageButtonActivity : AppCompatActivity() {

    lateinit var binding : ActivityImageButtonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button.setOnClickListener {
            startActivity(Intent(this, ChipActivity::class.java))
        }


    }
}