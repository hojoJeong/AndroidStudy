package com.ssafy.android

import android.app.Application
import android.util.Log
import com.ssafy.android.util.ForegroundDetector

private const val TAG = "MyApplication_싸피"

class MyApplication : Application() {
    private lateinit var foregroundDetector: ForegroundDetector

    override fun onCreate() {
        super.onCreate()
        foregroundDetector = ForegroundDetector(this)
        foregroundDetector.addListener(object : ForegroundDetector.Listener {
            override fun onBecameForeground() {
                Log.d(TAG, "Became Foreground")
            }

            override fun onBecameBackground() {
                Log.d(TAG, "Became Background")
            }
        })
    }

    override fun onTerminate() {
        super.onTerminate()
        foregroundDetector.unregisterCallbacks()
    }
}