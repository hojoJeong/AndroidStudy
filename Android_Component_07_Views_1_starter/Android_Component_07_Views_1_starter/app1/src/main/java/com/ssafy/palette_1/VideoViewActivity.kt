package com.ssafy.palette_1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.ssafy.palette_1.databinding.ActivityVideoViewBinding

class VideoViewActivity : AppCompatActivity() {

    lateinit var binding : ActivityVideoViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val VIDEO_URL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val uri = Uri.parse(VIDEO_URL)

//        val VIDEO_PATH = "android.resource://" + packageName + "/" + R.raw.sample
//        val uri = Uri.parse(VIDEO_PATH)

        // VideoView 가 보여줄 동영상의 경로 주소(Uri) 설정
        binding.videoView.setVideoURI(uri)
        // Video 재생, 일시정지 등을 할 수 있는 컨트롤바 부착
        binding.videoView.setMediaController(MediaController(this))
        // Video 로딩 준비가 끝났을 때 동영상을 실행하도록 리스너 설정
        binding.videoView.setOnPreparedListener {
            binding.videoView.start()
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, CalendarViewActivity::class.java))
        }
    }
}