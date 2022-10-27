package com.ssafy.android.tag_recognition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ssafy.android.tag_recognition.databinding.ActivityMainBinding

/**
 * activity의 default launchMode : standard이므로 onCreate가 계속 호출된다.
 * 호출시마다 Activity를 재생성
 */
private const val TAG = "MainActivity_싸피"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val action = intent.action
        binding.textview.text = action
        Toast.makeText(this, "onCreate$action", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onCreate: $action")
    }

    @Override
    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
    }

}