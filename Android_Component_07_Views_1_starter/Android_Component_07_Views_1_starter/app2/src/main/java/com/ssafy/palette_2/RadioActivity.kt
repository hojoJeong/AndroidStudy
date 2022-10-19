package com.ssafy.palette_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.palette_2.databinding.ActivityRadioBinding

class RadioActivity : AppCompatActivity() {

    lateinit var binding : ActivityRadioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRadioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.resultBtn.setOnClickListener {

            when(binding.radioGroup.checkedRadioButtonId){

                binding.radioBtn1.id -> {
                    binding.selectResultTxt.text = "1번을 선택하였습니다."
                }
                binding.radioBtn2.id -> {
                    binding.selectResultTxt.text = "2번을 선택하였습니다."
                }
                binding.radioBtn3.id -> {
                    binding.selectResultTxt.text = "3번을 선택하였습니다."
                }
                binding.radioBtn4.id -> {
                    binding.selectResultTxt.text = "4번을 선택하였습니다."
                }
            }
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, ToggleActivity::class.java))
        }
    }
}