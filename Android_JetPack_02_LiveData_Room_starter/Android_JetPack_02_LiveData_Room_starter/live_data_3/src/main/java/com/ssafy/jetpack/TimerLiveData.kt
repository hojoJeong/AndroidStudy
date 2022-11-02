package com.ssafy.jetpack

import android.os.SystemClock
import androidx.lifecycle.LiveData
import java.util.*


class TimerLiveData : LiveData<Long>() {

    private val initialTime: Long = SystemClock.elapsedRealtime()
    private var timer: Timer? = null

    override fun onActive() {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - initialTime) / 1000
                postValue(newValue)
            }
        }, ONE_SECOND, ONE_SECOND)
    }

    override fun onInactive() {
        timer?.cancel()
    }

    companion object {
        private const val ONE_SECOND = 1000.toLong()
    }
}