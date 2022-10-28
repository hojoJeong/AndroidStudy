package com.ssafy.android.tag_writer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.android.tag_writer.databinding.ActivityMainBinding

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.button0.setOnClickListener { view: View? ->
            startActivity(Intent(this@MainActivity, WriteMainActivity::class.java))
        }
        
        binding.button1.setOnClickListener { view: View? ->
            startActivity(Intent(this@MainActivity, WriteWithDialogActivity::class.java))
        }
    }

}