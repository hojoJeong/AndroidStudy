package com.ssafy.palette_1.progressbar

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.ssafy.palette_1.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) : Dialog(context) {

    lateinit var binding : DialogLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 배경 투명하게
        window!!.setBackgroundDrawable(ColorDrawable())
    }
}