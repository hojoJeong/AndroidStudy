package com.ssafy.d_android_coroutine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.d_android_coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                10.countDown(++currentIndex)
            }
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, CoroutineScopeActivity::class.java))
        }
    }

    var currentIndex = 0

    private suspend fun Int.countDown(currentIndex: Int) {
        for (index in this downTo 1) {
            binding.textView.text = "Now index $currentIndex Countdown $index"
            delay (1000)
        }
        Log.i (TAG, "Now index $currentIndex Done!")
    }

}