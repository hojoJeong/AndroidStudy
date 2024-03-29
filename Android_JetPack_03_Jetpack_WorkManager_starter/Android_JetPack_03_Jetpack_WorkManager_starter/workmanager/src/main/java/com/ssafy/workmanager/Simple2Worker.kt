package com.ssafy.workmanager

import android.content.Context
import android.os.SystemClock
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class Simple2Worker(context : Context, params : WorkerParameters) : Worker(context, params) {

    companion object {
        const val EXTRA_RESULT = "EXTRA_RESULT"
    }

    override fun doWork(): Result {
        val number = inputData.getInt(EXTRA_RESULT, 0)
        val result = number * 2
        SystemClock.sleep(5000)
        Log.d("Simple2Worker", "Simple2Worker finished: $result")

        val outputData = Data.Builder()
            .putInt(EXTRA_RESULT, result)
            .build()

        return Result.success(outputData)
    }
}
