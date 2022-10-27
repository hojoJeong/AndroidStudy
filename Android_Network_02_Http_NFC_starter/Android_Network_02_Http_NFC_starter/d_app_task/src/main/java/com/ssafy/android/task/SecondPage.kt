package com.ssafy.android.task

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "SecondPage_싸피"
class SecondPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContentView(R.layout.second)
        val b: Button = findViewById(R.id.next2)
        b.setOnClickListener {
            val intent = Intent("Third")
            startActivity(intent)
        }

        val b2: Button = findViewById(R.id.my2)
        b2.setOnClickListener {

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected override fun onResume() {
        val m: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks: List<ActivityManager.AppTask> = m.appTasks
        for (task in tasks) {
            val info: ActivityManager.RecentTaskInfo = task.taskInfo
            val cnt: Int = info.numActivities
            val id: Int = info.id
            val cName: String = info.baseActivity!!.shortClassName
            val sName: String = info.topActivity!!.shortClassName
            Log.d(TAG, "baseActivity:$cName, top:$sName, numActivities:$cnt, id:$id")
        }
        super.onResume()
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}