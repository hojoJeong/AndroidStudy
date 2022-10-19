package com.ssafy.palette_3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.palette_3.databinding.ActivityScrollViewBinding

class ScrollViewActivity  : AppCompatActivity() {

    private lateinit var binding: ActivityScrollViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myAdapter = MyAdapter(arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"))

        binding = ActivityScrollViewBinding.inflate(layoutInflater).apply {
            mainRv2.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = myAdapter
            }
        }
        setContentView(binding.root)
    }
}

