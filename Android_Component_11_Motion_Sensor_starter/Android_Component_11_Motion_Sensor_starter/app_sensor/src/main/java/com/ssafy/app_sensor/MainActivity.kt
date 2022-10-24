package com.ssafy.app_sensor

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.app_sensor.databinding.ActivityMainBinding

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var sensorManager: SensorManager
    private lateinit var accelometerSensor:Sensor

    private val list: MutableList<ImageViewData> = arrayListOf()

    private var realWidth:Int = 0
    private var realHeight:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//      //action bar 숨기기
//        if(supportActionBar?.isShowing() == true){
//            supportActionBar?.hide()
//        }

        initSensorManager()
        calcReadDeviceSize()
        initTouchListener()
    }

    private fun initSensorManager() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    private fun calcReadDeviceSize() {
//        //상단 status bar 높이 구하기. <-- Notification 보여주는 영역
//        var statusBarHeight = 0
//        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
//        if (resourceId > 0) statusBarHeight = resources.getDimensionPixelSize(resourceId)
//        Log.d(TAG, "calcReadDeviceSize: ${statusBarHeight}")

//        //action bar 높이
//        val typed = TypedValue()
//        theme.resolveAttribute(android.R.attr.actionBarSize, typed, true)
//        val actionBarHeight =  resources.getDimensionPixelSize(typed.resourceId)
//        Log.d(TAG, "calcReadDeviceSize: actionBarHeight : ${actionBarHeight}")

//        //하단 navigation bar 높이 구하기. <-- 뒤로 가기 버튼있는 영역.
//        var bottomBarHeight = 0
//        val resourceIdBottom = resources.getIdentifier("navigation_bar_height", "dimen", "android")
//        if (resourceIdBottom > 0) bottomBarHeight = resources.getDimensionPixelSize(resourceIdBottom)
//        Log.d(TAG, "calcReadDeviceSize: ${bottomBarHeight}")


        //30부터 windowManager.getDefaultDisplay()가 deprecated되었음.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display =  windowManager.currentWindowMetrics

            realWidth = display.bounds.width()
            realHeight = display.bounds.height()

            val systemBars = display.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
//            Log.d(TAG, "calcReadDeviceSize: systemBars:${systemBars}")

//            //action bar 높이
            val typed = TypedValue()
            theme.resolveAttribute(android.R.attr.actionBarSize, typed, true)
            val actionBarHeight =  resources.getDimensionPixelSize(typed.resourceId)
//            Log.d(TAG, "calcReadDeviceSize: actionBarHeight : ${actionBarHeight}")

            realHeight = display.bounds.height() - systemBars.top - systemBars.bottom - actionBarHeight
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            realWidth = displayMetrics.widthPixels
            realHeight = displayMetrics.heightPixels
        }
        Log.d(TAG, "calcReadDeviceSize: ${realWidth}, ${realHeight}")
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTouchListener() {
        binding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                addImageView(event.x, event.y)
            }
            true
        }
    }

    private fun addImageView(eventX: Float, eventY: Float) {
        val imageView = ImageView(this).apply {
            setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            layoutParams = LinearLayout.LayoutParams(SIZE, SIZE)
            //클릭되는 곳에 이미지를 놓기 위해서 계산.
            // 계산안하면,  클릭위치에 이미지의 좌상단(0,0)이 놓이게 된다.
            x = eventX - SIZE / 2
            y = eventY - SIZE / 2
        }
        binding.root.addView(imageView)
        list.add(ImageViewData(imageView))
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(listener, accelometerSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        sensorManager.unregisterListener(listener)
        super.onPause()
    }

    private fun getCalcWidth(): Int {
        return realWidth - SIZE
    }

    private fun getCalcHeight(): Int {
        return realHeight - SIZE
    }
    private var listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor == accelometerSensor) {
                list.map {
                    val newX = event.values[0] * it.speed
                    val newY = event.values[1] * it.speed

                    with(it.imageView) {
                        x -= newX
                        y += newY

                        if (x > getCalcWidth()){
                            x = getCalcWidth().toFloat()
                        }
                        if (x < 0){
                            x = 0f
                        }

                        if (y > getCalcHeight()){
                            y = getCalcHeight().toFloat()
                        }
                        if (y < 0){
                            y = 0f
                        }
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

    }

    companion object {
        private const val SIZE = 100
    }
}