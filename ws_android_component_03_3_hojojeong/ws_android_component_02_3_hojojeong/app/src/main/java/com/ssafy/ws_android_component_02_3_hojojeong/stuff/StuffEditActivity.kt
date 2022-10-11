package com.ssafy.ws_android_component_02_3_hojojeong.stuff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.ws_android_component_02_3_hojojeong.databinding.ActivityStuffEditBinding
import com.ssafy.ws_android_component_02_3_hojojeong.dto.StuffDto

private const val TAG = "StuffEditActivity_싸피"
class StuffEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuffEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var stuffDto: StuffDto
        binding = ActivityStuffEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var code = intent.getStringExtra("code")
        var position = intent.getIntExtra("position", 0)

        if (code.equals("modify")) {
            var item = intent.getSerializableExtra("stuff") as StuffDto
            binding.edEditTitle.hint = item.stuffName
            binding.edEditQuantity.hint = item.quantity.toString()
        }

        binding.btnEditSave.setOnClickListener{
            var stuffName = binding.edEditTitle.text.toString()
            var quantity: String

            if(binding.edEditQuantity.text.toString().isEmpty()){
                quantity = binding.edEditQuantity.hint.toString()
            } else{
                quantity = binding.edEditQuantity.text.toString()
            }
            stuffDto = StuffDto(stuffName = stuffName, quantity = quantity.toInt())

            intent.putExtra("stuff", stuffDto)
            intent.putExtra("position", position)
            if(code.equals("modify")){
                intent.putExtra("code", "modify")
            } else{
                intent.putExtra("code", "add")
            }
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.btnEditDelete.setOnClickListener{
            intent.putExtra("code", "delete")
            intent.putExtra("index", position)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.btnEditCancel.setOnClickListener{
            finish()
        }
    }
}