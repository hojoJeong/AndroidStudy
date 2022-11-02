package com.ssafy.jetpack

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {

    private val liveString = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = findViewById<TextView>(R.id.text)
        //백그라운드 스레드에서 호출
        liveString.postValue("postValue Write")

        //반드시 메인 스레드에서만 호출
        liveString.value = "setValue Write"

        liveString.observe(this){ liveString ->
            Log.d(TAG, "value : $liveString")
            text.text = liveString
        }

        //결과
//        D/MainActivity_싸피: value : setValue Write
//        D/MainActivity_싸피: value : postValue Write
    }
}