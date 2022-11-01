package com.ssafy.databinding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.databinding.databinding.ActivityMainBinding
import kotlin.random.Random

private const val TAG = "MainActivity_싸피"
class MainActivity: AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "no Databinding"

        var userList = arrayOf(User("길동", "홍"), User("순신", "이"), User("사임당", "신"))

        binding.nextButton.setOnClickListener {
            var rand =  Random.nextInt(3)
            binding.firstnameTextView.text = userList[rand].firstName
            binding.lastnameTextView.text = userList[rand].lastName
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, MainActivityWithDataBinding::class.java))
        }
    }
}