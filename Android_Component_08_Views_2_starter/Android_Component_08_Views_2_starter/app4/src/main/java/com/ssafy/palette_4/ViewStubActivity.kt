package com.ssafy.palette_4

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.palette_4.databinding.ActivityViewStubBinding

class ViewStubActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewStubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewStubBinding.inflate(layoutInflater).apply {
            mainBtnShow.setOnClickListener {
                mainStub.visibility = View.VISIBLE
//                if (mainStub.parent is ViewGroup) {
//                    mainStub.inflate()
//                }
            }
            mainBtnHide.setOnClickListener {
                mainStub.visibility = View.GONE
            }
        }
        setContentView(binding.root)
    }
}
