package com.ssafy.android.beacon_scanner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

private const val TAG = "BeaconReceiver_싸피"

@RequiresApi(api = Build.VERSION_CODES.O)
class BeaconReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: ")
        val it = Intent(context, EurekaActivity::class.java)
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(it)
    }

}