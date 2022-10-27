package com.ssafy.thread

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.ssafy.thread.databinding.ActivityMainBinding
import com.ssafy.thread.databinding.ActivityStartBinding

private const val TAG = "StartActivity_싸피"
class StartActivity : AppCompatActivity() {

    private var isRunning = false
    lateinit var binding : ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNext.setOnClickListener {
            startActivity(Intent(this, StartActivityWithHandler::class.java))
        }

        isRunning = true
        val thread = ThreadClass()
        thread.start()
    }

    inner class ThreadClass : Thread() {
        override fun run() {
            while(isRunning) {
                sleep(100)
                Log.d(TAG, System.currentTimeMillis().toString())
//                binding.helloTextview.text = System.currentTimeMillis().toString()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()

        isRunning = true
    }
    override fun onStop() {
        super.onStop()

        isRunning = false
    }
}















