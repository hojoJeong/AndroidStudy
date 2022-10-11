package com.ssafy.ws_android_component_03_3_hojojeong.stuff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.ws_android_component_03_3_hojojeong.databinding.ActivityStuffEditBinding
import com.ssafy.ws_android_component_03_3_hojojeong.dto.StuffDto

private const val TAG = "StuffEditActivity_싸피"

class StuffEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuffEditBinding
    private lateinit var stuffDto: StuffDto
    private lateinit var item: StuffDto
    private lateinit var code : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStuffEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        code = intent.getStringExtra("code")!!

        if (code.equals("modify")) {
            initItemIfModify()
        }
        deleteListener()
        saveListener()

        binding.btnEditCancel.setOnClickListener {
            finish()
        }
    }

    private fun initItemIfModify() {
        item = intent.getSerializableExtra("stuff") as StuffDto
        binding.edEditTitle.hint = item.stuffName
        binding.edEditQuantity.hint = item.quantity.toString()
    }

    private fun deleteListener() {
        binding.btnEditDelete.setOnClickListener {
            intent.putExtra("code", "delete")
            intent.putExtra("id", item._id)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun saveListener() {
        binding.btnEditSave.setOnClickListener {
            val stuffName = binding.edEditTitle.text.toString()
            val quantity: String

            if (binding.edEditQuantity.text.toString().isEmpty()) {
                quantity = binding.edEditQuantity.hint.toString()
            } else {
                quantity = binding.edEditQuantity.text.toString()
            }
            stuffDto = StuffDto(_id = item._id, stuffName = stuffName, quantity = quantity.toInt())

            intent.putExtra("stuff", stuffDto)
            if (code.equals("modify")) {
                intent.putExtra("code", "modify")
            } else {
                intent.putExtra("code", "add")
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}