package com.ssafy.databinding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ssafy.databinding.databinding.ActivityMainWithDatabindingBinding
import kotlin.random.Random

class MainActivityWithDataBinding: AppCompatActivity(){

    private lateinit var binding: ActivityMainWithDatabindingBinding
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState);

        title = "with Databinding"

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_with_databinding)

        //혹은 기존방식으로...
//        binding = ActivityMainWithDatabindingBinding.inflate(layoutInflater)
//        setContentView(binding.root)


        var userList = arrayOf(User("길동", "홍"), User("순신", "이"), User("사임당", "신"))

        binding.activity = this;

        binding.nextButton.setOnClickListener {
            var rand =  Random.nextInt(3)
            binding.user = userList[rand];
        }
    }

    // click event 처리.
    fun onButtonClick(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}