package com.ssafy.android.tag_writer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.android.tag_writer.TagWriteActivity
import com.ssafy.android.tag_writer.databinding.ActivityWriteMainBinding

private const val TAG = "WriteMainActivity_싸피"
class WriteMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //버튼 클릭시 입력된 데이터와 태그 타입을 Extras에 담아서 TagWrite전달...
    fun writeText(view: View?) {
        val data = binding.textEt.text.toString()
        val intent = Intent(this, TagWriteActivity::class.java)
        intent.putExtra("data", data)
        intent.putExtra("type", "T")
        startActivity(intent)
    }

    fun writeUrl(view: View?) {
        val data = binding.urlEt.text.toString()
        Log.d(TAG, "writeUrl: $data")
        val intent = Intent(this, TagWriteActivity::class.java)
        intent.putExtra("data", data)
        intent.putExtra("type", "U")
        startActivity(intent)
    }
}