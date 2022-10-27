package com.ssafy.android.task

import android.app.ActivityManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "ThirdPage_싸피"
class ThirdPage : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContentView(R.layout.third)
        val b = findViewById<Button>(R.id.next3)
        b.setOnClickListener {
            val intent = Intent("First")
            startActivity(intent)
        }

        val b2: Button = findViewById(R.id.my3)
        b2.setOnClickListener {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onResume() {
        val m = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val tasks = m.appTasks
        Log.d(TAG, "onResume: tasks.size : ${tasks.size}")
        for (task in tasks) {
            val info = task.taskInfo
            val cnt = info.numActivities
            val id = info.id
            val cName = info.baseActivity!!.shortClassName
            val sName = info.topActivity!!.shortClassName
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