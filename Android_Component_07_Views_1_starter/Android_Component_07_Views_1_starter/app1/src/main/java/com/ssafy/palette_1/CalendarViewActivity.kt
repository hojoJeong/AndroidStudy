package com.ssafy.palette_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ssafy.palette_1.databinding.ActivityCalendarViewBinding
import com.ssafy.palette_1.progressbar.ProgressBarActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CalendarViewActivity_싸피"
class CalendarViewActivity : AppCompatActivity() {

    lateinit var binding : ActivityCalendarViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 캘린더 인스턴스 가져오기
        val calendar = Calendar.getInstance()

        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 0)
        binding.calendarView.date = cal.time.time

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // 캘린더 인스턴스에 CalendarView 에서 선택한 날짜 세팅
            calendar.set(year, month, dayOfMonth)

            // 날짜 표기법 Format
            val dateFormatter = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
            dateFormatter.timeZone = TimeZone.getTimeZone("Asia/Seoul")
//            val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
            val formattedDate = dateFormatter.format(calendar.time)

            // TextView 에 날짜 세팅하기
            binding.dateTv.text = formattedDate

        }

        val today = Date()
        val dateFull = DateFormat.getDateInstance(DateFormat.FULL)
        val dateLong = DateFormat.getDateInstance(DateFormat.LONG)
        val dateMedium = DateFormat.getDateInstance(DateFormat.MEDIUM)
        val dateShort = DateFormat.getDateInstance(DateFormat.SHORT)

        Log.d(TAG, "FULL : ${dateFull.format(today)}")
        Log.d(TAG, "LONG : ${dateLong.format(today)}")
        Log.d(TAG, "MEDIUM : ${dateMedium.format(today)}")
        Log.d(TAG, "SHORT : ${dateShort.format(today)}")


        binding.button.setOnClickListener {
            startActivity(Intent(this, ProgressBarActivity::class.java))
        }
    }
}