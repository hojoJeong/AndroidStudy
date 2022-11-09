package com.ssafy.android.beacon_scanner

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.android.util.ForegroundDetector

private const val TAG = "BaseActivity_싸피"

//AppCompactActivity를 상속받는 BaseActivity
//현재 Application 내의 Activity에서 공통적으로 수행해야할 업무를 정의한다.
//여기서는 myScanner 를 공유한다. *(어느 화면이든 background로 내려가면, scanning 중지)
@RequiresApi(api = Build.VERSION_CODES.O)
open class BaseActivity : AppCompatActivity() {
    lateinit var myScanner: MyBeaconScanner

    override fun onStop() {
        super.onStop()
        if (ForegroundDetector.instance.isBackground) {
            Log.d(TAG, "BaseActivity.onStop: ")
            Log.d(TAG, "Application isBackground: destroying myScanner")
            myScanner!!.destroy()
        }
    }

}