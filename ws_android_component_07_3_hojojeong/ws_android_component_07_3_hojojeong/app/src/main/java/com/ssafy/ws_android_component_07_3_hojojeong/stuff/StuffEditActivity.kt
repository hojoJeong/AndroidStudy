package com.ssafy.ws_android_component_07_3_hojojeong.stuff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ssafy.ws_android_component_07_3_hojojeong.databinding.ActivityStuffEditBinding
import com.ssafy.ws_android_component_07_3_hojojeong.dto.StuffDto
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "StuffEditActivity_싸피"

class StuffEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuffEditBinding
    private lateinit var stuffDto: StuffDto
    private lateinit var item: StuffDto
    private lateinit var code: String
    private lateinit var calendar: Calendar
    private var stuffRegDate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStuffEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        calendarChangeListener()
        deleteEventListener()
        saveEventListener()
        binding.btnEditCancel.setOnClickListener {
            finish()
        }
    }

    private fun init() {
        code = intent.getStringExtra("code")!!
        calendar = Calendar.getInstance()
        binding.tvDateEdit.text =
            SimpleDateFormat("yy / MM / dd", Locale.KOREA).format(calendar.time)

        when (code) {
            "modify" -> {
                item = intent.getSerializableExtra("stuff") as StuffDto
                Log.d(TAG, "initItemIfModify: $item")
                binding.edEditTitle.hint = item.stuffName
                binding.edEditQuantity.hint = item.quantity.toString()
                stuffRegDate = item.regDate
                binding.tvDateEdit.text =
                    SimpleDateFormat("yy / MM / dd", Locale.KOREA).format(item.regDate)
            }
            "add" -> {
                binding.btnEditDelete.visibility = View.GONE
            }
        }

        when (stuffRegDate) {
            0L -> {
                calendar.add(Calendar.DATE, 0)
                stuffRegDate = calendar.time.time
            }
            else -> {
                binding.calendarViewEdit.date = stuffRegDate
            }
        }
    }

    private fun saveEventListener() {
        binding.btnEditSave.setOnClickListener {
            if (code == "add" && (binding.edEditTitle.text.isEmpty() || binding.edEditQuantity.text.isEmpty())) {
                Toast.makeText(this, "입력을 확인해주세요", Toast.LENGTH_SHORT).show()
            } else {
                val stuffName = binding.edEditTitle.text.toString().ifEmpty {
                    binding.edEditTitle.hint.toString()
                }
                val quantity = binding.edEditQuantity.text.toString().ifEmpty {
                    binding.edEditQuantity.hint.toString()
                }
                when (code) {
                    "modify" -> {
                        stuffDto = StuffDto(
                            _id = item._id,
                            stuffName = stuffName,
                            quantity = quantity.toInt(),
                            regDate = stuffRegDate
                        )
                        intent.putExtra("code", "modify")
                    }
                    "add" -> {
                        stuffDto = StuffDto(
                            stuffName = stuffName,
                            quantity = quantity.toInt(),
                            regDate = stuffRegDate
                        )
                        intent.putExtra("code", "add")
                    }
                }
                intent.putExtra("stuff", stuffDto)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun deleteEventListener() {
        binding.btnEditDelete.setOnClickListener {
            Log.d(TAG, "deleteEventListener 아이템 아이디: $item")
            intent.putExtra("code", "delete")
            intent.putExtra("id", item._id)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun calendarChangeListener() {
        binding.calendarViewEdit.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val dateFomatter = SimpleDateFormat("yy / MM / dd", Locale.KOREA)
            dateFomatter.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            val formattedDate = dateFomatter.format(calendar.time)
            Log.d(TAG, "calendarChangeListener: $stuffRegDate")
            binding.tvDateEdit.text = formattedDate

            stuffRegDate = calendar.time.time
        }
    }
}