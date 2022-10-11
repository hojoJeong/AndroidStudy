package com.ssafy.ws_android_component_02_3_hojojeong.stuff

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.ws_android_component_02_3_hojojeong.databinding.ActivityStuffBinding
import com.ssafy.ws_android_component_02_3_hojojeong.dto.StuffDto

private const val TAG = "StuffActivity_싸피"
class StuffActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuffBinding
    private lateinit var stuffDto: ArrayList<StuffDto>
    private lateinit var adapter: ArrayAdapter<StuffDto>
    private lateinit var listviewStuffList: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStuffBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        listviewStuffList.setOnItemClickListener{parent, view, position, id ->
            val intent = Intent(this, StuffEditActivity::class.java)
            intent.putExtra("code", "modify")
            intent.putExtra("stuff", stuffDto[position])
            intent.putExtra("position", position)
            requestActivity.launch(intent)
        }

        binding.btnStuffAdd.setOnClickListener{
            val intent = Intent(this, StuffEditActivity::class.java)
            requestActivity.launch(intent)
        }
    }

    private  val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            Log.d(TAG, "result : check")
            val intent = it.data
            val code = intent?.getStringExtra("code")
            when(code){
                "add" ->{
                    var stuffDtoItem = intent.getSerializableExtra("stuff") as StuffDto
                    stuffDto.add(stuffDtoItem)
                    adapter.notifyDataSetChanged()
                }
                "modify" -> {
                    var index = intent.getIntExtra("position", 0)
                    var stuffDtoItem = intent.getSerializableExtra("stuff") as StuffDto
                    stuffDto.set(index, stuffDtoItem)
                    adapter.notifyDataSetChanged()
                }
                "delete" -> {
                    Log.d(TAG, "delete : check")
                    var index = intent.getIntExtra("position", 0)
                    stuffDto.removeAt(index)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun init(){
        listviewStuffList = binding.listviewStuff
        stuffDto = intent.getSerializableExtra("stuff") as ArrayList<StuffDto>
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stuffDto)
        listviewStuffList.adapter = adapter
    }
}
