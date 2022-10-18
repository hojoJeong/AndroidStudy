package com.ssafy.ws_android_component_04_3_hojojeong.stuff

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.ws_android_component_04_3_hojojeong.databinding.ActivityStuffBinding
import com.ssafy.ws_android_component_04_3_hojojeong.dto.StuffDto
import com.ssafy.ws_android_component_04_3_hojojeong.service.BoundService

private const val TAG = "StuffActivity_싸피"
class StuffActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuffBinding
    private lateinit var boundService: BoundService
    private lateinit var stuffList: ArrayList<StuffDto>
    private lateinit var adapter: ArrayAdapter<StuffDto>
    private lateinit var listviewStuffList: ListView
    private var isBound = false


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: ")
            val binder = service as BoundService.MyLocalBinder
            boundService = binder.getService()
            isBound = true
            init()

            listviewStuffList.setOnItemClickListener{parent, view, position, id ->
                val intent = Intent(this@StuffActivity, StuffEditActivity::class.java)
                intent.putExtra("code", "modify")
                intent.putExtra("stuff", stuffList[position])
//            intent.putExtra("position", position)
                requestActivity.launch(intent)
            }

            binding.btnStuffAdd.setOnClickListener{
                val intent = Intent(this@StuffActivity, StuffEditActivity::class.java)
                intent.putExtra("code", "add")
                requestActivity.launch(intent)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: ")
            isBound = false
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStuffBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, BoundService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

    private fun refreshRecyclerView() {
        stuffList.clear()
        stuffList.addAll(boundService.selectAll())
        adapter.notifyDataSetChanged()
    }

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            Log.d(TAG, "result : check")
            val intent = it.data
            when(intent?.getStringExtra("code")){
                "add" ->{
                    Log.d(TAG, "추가: ")
                    val stuffDtoItem = intent.getSerializableExtra("stuff") as StuffDto
                    boundService.insert(stuffDtoItem)
                    refreshRecyclerView()
                }
                "modify" -> {
                    val stuffDtoItem = intent.getSerializableExtra("stuff") as StuffDto
                    Log.d(TAG, "업데이트: ${stuffDtoItem}")
                    boundService.update(stuffDtoItem)
                    refreshRecyclerView()
                }
                "delete" -> {
                    val id = intent.getIntExtra("id", 0)
                    boundService.delete(id)
                    refreshRecyclerView()
                }
            }
        }
    }

    fun init(){
        listviewStuffList = binding.listviewStuff
        stuffList = boundService.selectAll() as ArrayList<StuffDto>
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stuffList)
        listviewStuffList.adapter = adapter
    }
}
