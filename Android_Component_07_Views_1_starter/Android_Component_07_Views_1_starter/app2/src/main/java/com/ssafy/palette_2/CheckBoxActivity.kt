package com.ssafy.palette_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.palette_2.databinding.ActivityCheckBoxBinding

class CheckBoxActivity : AppCompatActivity() {

    lateinit var binding : ActivityCheckBoxBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBoxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.check1.setOnClickListener {
            if (binding.check1.isChecked) {
                // TODO : CheckBox is checked.
            } else {
                // TODO : CheckBox is unchecked.
            }
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, RadioActivity::class.java))
        }
    }
}