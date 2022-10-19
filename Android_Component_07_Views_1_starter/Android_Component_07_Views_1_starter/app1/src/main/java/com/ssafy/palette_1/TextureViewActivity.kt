package com.ssafy.palette_1

import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.widget.LinearLayout
import com.ssafy.palette_1.databinding.ActivityTextureViewBinding

private const val TAG = "TextureViewActivity 싸피"
class TextureViewActivity : AppCompatActivity() {

    lateinit var binding : ActivityTextureViewBinding

    lateinit var mPlayer : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextureViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val VIDEO_PATH = "android.resource://" + packageName + "/" + R.raw.sample
        val uri = Uri.parse(VIDEO_PATH)

        binding.textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {

            // Texture Size 가 변경되면 호출되는 메서드입니다.
            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
                Log.d(TAG, "onSurfaceTextureSizeChanged: 화면 사이즈 변경됨~")
            }

            // Texture 가 파괴되면 호출되는 메서드입니다.
            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                Log.d(TAG, "onSurfaceTextureDestroyed: ")
                return false
            }

            // Texture 업데이트가 발생하면 호출되는 메서드입니다.
            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                Log.d(TAG, "onSurfaceTextureUpdated: ")
            }

            // Texture 가 준비되면 호출되는 메서드입니다. 초기화 처리를 해줍니다.
            override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
                Log.d(TAG, "onSurfaceTextureAvailable: ")
                // MediaPlayer 를 만들어줍니다.
                mPlayer = MediaPlayer()

                // 인자로 받은 surfaceTexture 를 기반으로 Surface 를 생성해줍니다.
                val surface = Surface(surfaceTexture)

                // 생성한 Surface 를 MediaPlayer 에 세팅해줍니다.
                mPlayer.setSurface(surface)

                mPlayer.setDataSource(this@TextureViewActivity, uri)

                // MediaPlayer 를 준비시켜줍니다.
                // 관련 메소드로는 prepare()와 prepareAsync()가 있습니다.
                // prepare()는 동기 처리, prepareAsync()는 비동기 처리에 사용됩니다.
                // 로컬 파일의 경우 prepare()로 동기처리를 해도 되지만,
                // 스트리밍 기능을 개발하는 경우에는 prepareAsync()로 비동기 처리를 해야합니다.

                mPlayer.prepare()
                var width = 0
                var height = 0
                binding.textureView.layoutParams = LinearLayout.LayoutParams(mPlayer.videoWidth, mPlayer.videoHeight)


                val display = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val disply = windowManager.currentWindowMetrics
                    width = disply.bounds.width()
                    height = disply.bounds.height()
                } else {
                    TODO("VERSION.SDK_INT < R")
                }
                mPlayer.setOnPreparedListener { mp ->
                    // 준비가 완료되면, 비디오를 재생합니다.
                    mp.start()
                }
            }


        }
    }
}