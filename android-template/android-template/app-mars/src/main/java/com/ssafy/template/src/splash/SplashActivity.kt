package com.ssafy.template.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.ssafy.template.config.BaseActivity
import com.ssafy.template.databinding.ActivitySplashBinding
import com.ssafy.template.src.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500)
    }
}