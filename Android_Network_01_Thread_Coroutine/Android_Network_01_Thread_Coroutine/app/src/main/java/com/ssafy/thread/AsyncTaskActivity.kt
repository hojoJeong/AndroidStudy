package com.ssafy.thread

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.TextView
import com.ssafy.thread.databinding.ActivityAsyncTaskBinding

@Suppress("DEPRECATION")

class AsyncTaskActivity : AppCompatActivity() {

    lateinit var binding : ActivityAsyncTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsyncTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val asyncTask = MyAsyncTask(binding.textView)
        asyncTask.execute()

    }

    inner class MyAsyncTask(textView: TextView) : AsyncTask<Void, Int, Boolean>() {

        override fun doInBackground(vararg params: Void?): Boolean {
            for(i in 0..500) {
                SystemClock.sleep(100)
                publishProgress(i)
            }
            return true
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            //vararg : 가변인자, "*"을 붙이면 가변인자로 취급
            binding.textView.text = values[0].toString()
        }

        override fun onCancelled(boolean: Boolean) {
            super.onCancelled()
        }
    }
}