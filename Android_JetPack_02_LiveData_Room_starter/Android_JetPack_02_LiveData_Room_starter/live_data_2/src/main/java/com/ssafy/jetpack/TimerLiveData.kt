package com.ssafy.jetpack

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import java.util.*

private const val TAG = "TimerLiveData_싸피"
class TimerLiveData : LiveData<Long>() {

    private val initialTime: Long = SystemClock.elapsedRealtime()
    private var timer: Timer? = null

    override fun onActive() {
        Log.d(TAG, "onActive: ")
        timer = Timer()
//         scheduleAtFixedRate(task, start, peroid) : start시간부터 period 간격으로 task를  수행
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - initialTime) / 1000
                postValue(newValue)
            }
        }, ONE_SECOND, ONE_SECOND)
    }

    override fun onInactive() {
        Log.d(TAG, "onInactive: ")
        timer?.cancel()
    }

    companion object {
        private const val ONE_SECOND = 1000.toLong()
    }
}