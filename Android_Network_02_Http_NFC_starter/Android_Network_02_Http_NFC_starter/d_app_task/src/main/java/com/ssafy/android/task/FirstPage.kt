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

private const val TAG = "FirstPage_싸피"
class FirstPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContentView(R.layout.activity_main)
        val b: Button = findViewById(R.id.next1)
        b.setOnClickListener {
            val intent = Intent("Second")
            startActivity(intent)
        }

        val b3: Button = findViewById(R.id.next2)
        b3.setOnClickListener {
            val intent = Intent("Second")
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP + Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected override fun onResume() {
        val m: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks: List<ActivityManager.AppTask> = m.appTasks
        for (task in tasks) {
            val info: ActivityManager.RecentTaskInfo = task.getTaskInfo()
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