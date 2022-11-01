package com.ssafy.viewmodel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ssafy.viewmodel.databinding.ActivityMainBinding
import com.ssafy.viewmodel.fragments.NewActivityWithFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //VeiwModel 선언
    private  val viewModel: MainViewModel by lazy{
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.countText.text = viewModel.count.toString()

        //버튼 클릭시 카운트 증가
        binding.increaseButton.setOnClickListener {
            viewModel.increaseCount()
            binding.countText.text = viewModel.count.toString()
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, NewActivityWithFragment::class.java))
        }
    }
}