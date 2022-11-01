package com.ssafy.viewmodel

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ssafy.viewmodel.databinding.ActivityMainBinding


/*
회전 대응 : onPause, onResume에서 저장하는 방법, 혹은 회전 금지
 */

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {

    //ViewModel Provider로 뷰모델 생성
    private val viewModel: MainViewModel by lazy {
//        ViewModelProvider(this).get(MainViewModel::class.java)
        ViewModelProvider(this)[MainViewModel::class.java]// 위에랑 같은 코드 -> 연산자 오버로딩
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: ")
        binding.countText.text = viewModel.count.toString()

//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //버튼 클릭시 카운트 증가
        binding.increaseButton.setOnClickListener {
            viewModel.increaseCount()
            binding.countText.text = viewModel.count.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    companion object{
        private const val KEY_COUNT="count"
    }
}