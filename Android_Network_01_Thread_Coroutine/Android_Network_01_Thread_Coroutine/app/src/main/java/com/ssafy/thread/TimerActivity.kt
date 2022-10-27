package com.ssafy.thread

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.ssafy.thread.databinding.ActivityTimerBinding

private const val TAG = "TimerActivity_싸피"
@SuppressLint("SetTextI18n")
class TimerActivity : AppCompatActivity() {

    lateinit var binding : ActivityTimerBinding

    var timerTime = 30 // 30초 Default
    private lateinit var timer : TimerHandler // Timer 를 구현해주는 Handler 객체
    var isRunning = true
    var status = 0 // 0,1 : 시작/일시정지 , 2 : 정지

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        timer = TimerHandler()
        binding.timerTv.text = "Timer : $timerTime"
        binding.timerStartBtn.setOnClickListener {
            when(status) {
                0 -> { // Pause 상태라면 Restart!
                    status = 1
                    timer.sendEmptyMessage(0) // 0번 메시지 전송
                    binding.timerStartBtn.text = "Pause"
                }

                1 -> { // Start 상태라면 Pause!
                    status = 0
                    timer.sendEmptyMessage(1) // 1번 메시지 전송
                    binding.timerStartBtn.text = "Start"
                }
            }
        }

        binding.timerStopBtn.setOnClickListener { // Timer Stop!
            status = 0
            timer.sendEmptyMessage(2) // 2번 메시지 전송
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, RunOnUiThreadActivity::class.java))
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        // 앱 종료 시에 타이머 종료
        if(timer != null) {
            timer.removeMessages(0)
        }
        isRunning = false
    }

    inner class TimerHandler : Handler(Looper.getMainLooper()) {
        // 메시지를 처리해주는 함수
        // msg.what 의 번호에 따라 원하는 작업 구분
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what) {
                0 -> { // Start (Timer 구동 코드)
                    if(timerTime == 0) { // 30초 다 지났을 때
                        binding.timerTv.text = "Timer : $timerTime"
                        removeMessages(0)
                        return
                    }
                    binding.timerTv.text = "Timer : ${timerTime--}" // 1초씩 감소
                    sendEmptyMessageDelayed(0, 1000) // 1초 후에 OS 에 메시지 전송
                }
                1 -> { // Pause
                    removeMessages(0)
                    // 메시지 큐에 쌓여있던 0번 메시지 삭제
                    // 1초 전에 전송했던 메시지가 삭제되면서 정상적으로 Pause 수행

                    binding.timerTv.text = "Timer : $timerTime" // 현재 시간 표시
                }
                2 -> { // Stop
                    removeMessages(0) // 0번 메시지 삭제
                    timerTime = 30 // Timer 초기화
                    binding.timerTv.text = "Timer : $timerTime"
                    binding.timerStartBtn.text = "Start"

                }
            }
        }
    }
}