package com.ssafy.palette_1.progressbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.ssafy.palette_1.databinding.ActivityProgressBarBinding

class ProgressBarActivity : AppCompatActivity() {

    lateinit var binding : ActivityProgressBarBinding
    lateinit var loadingDialog: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingDialog = LoadingDialog(this)

        // ProgressBar 실행
        binding.progressBarBtn.setOnClickListener {
            loadingDialog.show()
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, ProgressBarActivity2::class.java))
        }

    }
}