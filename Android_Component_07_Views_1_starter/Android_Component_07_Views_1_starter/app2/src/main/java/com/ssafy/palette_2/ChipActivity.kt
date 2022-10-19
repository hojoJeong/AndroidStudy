package com.ssafy.palette_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.ssafy.palette_2.databinding.ActivityChipBinding
import com.google.android.material.chip.Chip

class ChipActivity : AppCompatActivity() {

    lateinit var binding : ActivityChipBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionChip.setOnClickListener {
            Toast.makeText(this, "action chip clicked", Toast.LENGTH_SHORT).show()
        }
        binding.inputChip.setOnCloseIconClickListener {
            Toast.makeText(this, "닫기 아이콘 눌렀네요", Toast.LENGTH_SHORT).show()
        }

        val list = arrayListOf<String>("호조","Option1", "Option2", "Option3", "Option4", "Option5", "Option6","Option7")

        for(i in list) {
            val chip = Chip(this)
            chip.text = i
            chip.isCheckable = true
            binding.chipGroupOptions.addView(chip)

        }


        binding.button.setOnClickListener {
            startActivity(Intent(this, CheckBoxActivity::class.java))
        }
    }


}