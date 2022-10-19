package com.ssafy.palette_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.ssafy.palette_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.loadUrl("https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp")

        //페이지가 로딩될 때 처리
        binding.webView.webViewClient = WebViewClient()

        //setting : 웹페이지의 액션 ㅓ리, 팝업, 다운로드 등 처리
        binding.webView.settings.javaScriptEnabled = true//웹뷰를 열었을 때 js로 짜여진 코드를 실행하려면 해당 라인을 추가해줘야함

//        binding.webView.loadUrl("https://m.naver.com")

        binding.button2.setOnClickListener {
            val url = binding.edtMain.text.toString()
            binding.webView.loadUrl(url)
        }
        binding.button.setOnClickListener {
            startActivity(Intent(this, VideoViewActivity::class.java))
        }
    }
}