package com.ssafy.ws_android_component_01_3_hojojeong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.ws_android_component_01_3_hojojeong.databinding.ActivityMainBinding
import com.ssafy.ws_android_component_01_3_hojojeong.dto.Stuff
import com.ssafy.ws_android_component_01_3_hojojeong.stuff.StuffActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var stuffList: ArrayList<Stuff> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        stuffList.add(Stuff("사과", 10))
        stuffList.add(Stuff("참외", 11))

        var stuff = Stuff("싸피벅스", "010-1234-5678", "36.10830144", "128.41827")
        stuffList
        binding.txtMainTitle.text = stuff.title
        binding.txtMainNumber.text = stuff.number
        binding.txtMainLat.text = stuff.lat
        binding.txtMainLong.text = stuff.long
        binding.txtMainTitle.setOnClickListener{
            val intent = Intent(this, StuffActivity::class.java)
            intent.putExtra("stuff", stuffList)
            startActivity(intent)
        }
    }
}