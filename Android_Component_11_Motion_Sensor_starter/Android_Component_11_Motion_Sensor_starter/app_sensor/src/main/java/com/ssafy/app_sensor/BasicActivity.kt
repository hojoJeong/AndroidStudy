package com.ssafy.app_sensor

import android.annotation.SuppressLint
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.app_sensor.databinding.ActivityBasicBinding
import java.util.*

//화면에 랜덤하게 하트 배치...
private const val TAG = "BasicActivity_싸피"
class BasicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBasicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTouchListener()
    }
    
    @SuppressLint("ClickableViewAccessibility")
    fun initTouchListener(){
        binding.root.setOnTouchListener { view, motionEvent ->  
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    Log.d(TAG, "initTouchListener: ${motionEvent.x}, ${motionEvent.y}")
                    setImageView(motionEvent.x, motionEvent.y)
                }
                
                else -> {
                    
                }
            }
            true
        }
    }


    val list = arrayListOf<ImageView>()
    private fun setImageView(eventX: Float, eventY: Float){
        val imageView = ImageView(this).apply {
            setBackgroundResource(R.drawable.ic_baseline_favorite_24)

            layoutParams = LinearLayout.LayoutParams(SIZE, SIZE)

            x = eventX - SIZE/2
            y = eventY - SIZE/2
        }

        imageView.setOnClickListener {
            Toast.makeText(this, "클릭했음", Toast.LENGTH_SHORT).show()
        }

        binding.root.addView(imageView)
        list.add(imageView)
    }

    companion object{
        private const val SIZE = 100
    }



    override fun onResume() {
        super.onResume()
        isRunning = true
        startThread()
    }
    override fun onPause() {
        super.onPause()
        isRunning = false
    }
    //1초마다 아래로 이동 --> Sensor 로부터 데이터를 받아서 이동
    //메인이 아닌 다른 Thread에서는 ui에 접근할 수 없음
    //하지만 UI를 변경할 수는 있음(하트를 하위로 이동)
    //MainThread Handler로 통신
    var isRunning = false

    private fun startThread(){
        Thread{
            while (isRunning){
                Thread.sleep(1000)      //main과 다른 Thread
                handler.post {  //post안에도 thread가 있음
                    list.map {
                        it.y += 10 + Random().nextInt(50) // 0 ~ 49
                    }
                }
            }
        }.start()
    }

    private val handler = Handler(Looper.getMainLooper())
}



