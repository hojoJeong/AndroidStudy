package com.ssafy.ws_android_component_01_3_hojojeong.stuff

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.ws_android_component_01_3_hojojeong.R
import com.ssafy.ws_android_component_01_3_hojojeong.databinding.ActivityStuffBinding
import com.ssafy.ws_android_component_01_3_hojojeong.dto.Stuff

private const val TAG = "StuffActivity_싸피"
class StuffActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuffBinding
    private lateinit var stuff: ArrayList<Stuff>
    private lateinit var adapter: ArrayAdapter<Stuff>
    private lateinit var listviewStuffList: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStuffBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        listviewStuffList.setOnItemClickListener{parent, view, position, id ->
            val intent = Intent(this, StuffEditActivity::class.java)
            intent.putExtra("code", "modify")
            intent.putExtra("stuff", stuff[position])
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
                    var stuffItem = intent.getSerializableExtra("stuff") as Stuff
                    stuff.add(stuffItem)
                    adapter.notifyDataSetChanged()
                }
                "modify" -> {
                    var index = intent.getIntExtra("position", 0)
                    var stuffItem = intent.getSerializableExtra("stuff") as Stuff
                    stuff.set(index, stuffItem)
                    adapter.notifyDataSetChanged()
                }
                "delete" -> {
                    Log.d(TAG, "delete : check")
                    var index = intent.getIntExtra("position", 0)
                    stuff.removeAt(index)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun init(){
        listviewStuffList = binding.listviewStuff
        stuff = intent.getSerializableExtra("stuff") as ArrayList<Stuff>
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stuff)
        listviewStuffList.adapter = adapter
    }
}
