package com.ssafy.palette_3

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.palette_3.databinding.ActivityMultiAutoBinding

class MultiAutoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultiAutoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val wordList = mutableListOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
        val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, wordList)

        binding = ActivityMultiAutoBinding.inflate(layoutInflater).apply {
            mainMultiAutoCompleteTv.apply {
                setAdapter(adapter)
                setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
            }
        }

        setContentView(binding.root)
    }
}