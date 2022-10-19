package com.ssafy.palette_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.palette_2.databinding.ActivitySwitchBinding

class SwitchActivity : AppCompatActivity() {

    lateinit var binding : ActivitySwitchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwitchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            when(isChecked) {
                true -> binding.switchTv.text = "Switch is checked"

                false -> binding.switchTv.text = "Switch is unchecked"
            }
        }
    }
}