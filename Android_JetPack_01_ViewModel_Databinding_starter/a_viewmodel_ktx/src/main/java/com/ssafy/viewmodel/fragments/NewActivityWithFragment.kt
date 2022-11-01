package com.ssafy.viewmodel.fragments

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.viewmodel.R
import com.ssafy.viewmodel.databinding.ActivityNewWithFragmentBinding

class NewActivityWithFragment : AppCompatActivity() {
    private val newActivityViewModel by viewModels<NewActivityViewModel>() // 위에랑 같음
    private lateinit var binding: ActivityNewWithFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewWithFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, BlankFragment1().apply{
            }).commit()

        binding.fragment1.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, BlankFragment1().apply{
                }).commit()
        }

        binding.fragment2.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, BlankFragment2().apply{
                }).commit()
        }
    }
}

