package com.ssafy.palette_4

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.palette_4.databinding.ActivityRequestFocusBinding

private const val TAG = "RequestFocusActivity 싸피"
class RequestFocusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRequestFocusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRequestFocusBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onRestart() {
        super.onRestart()
        toggleView()
    }

    private fun toggleView() {
        Log.d(TAG, "toggleView: $currentFocus")
        if (currentFocus?.visibility == View.VISIBLE) {
            Toast.makeText(this, "invisiable~", Toast.LENGTH_SHORT).show()
            currentFocus?.visibility = View.INVISIBLE
        } else {
            binding.mainEt.visibility = View.VISIBLE
            binding.mainEt.requestFocus()
            Toast.makeText(this, "visiable!!", Toast.LENGTH_SHORT).show()
        }
    }
}


