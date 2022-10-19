package com.ssafy.palette_3

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.palette_3.databinding.ActivityFloatingBinding
import com.google.android.material.snackbar.Snackbar

class FloatingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFloatingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFloatingBinding.inflate(layoutInflater).apply {
            fab.setOnClickListener { view ->
                Snackbar.make(view, "Snackbar Message", Snackbar.LENGTH_SHORT).show()

                toggle()

            }
        }

        binding.fabMail.setOnClickListener {
            Snackbar.make(it, "메일 보내기", Snackbar.LENGTH_SHORT).show()
        }
        setContentView(binding.root)
    }

    var isOpen = false
    fun toggle(){
        if(isOpen){
            ObjectAnimator.ofFloat(binding.fab, View.ROTATION, 45f, 0f ).apply {
                duration = 500
                start()
            }
            ObjectAnimator.ofFloat(binding.fabMail,"translationY", 0f ).apply {
                duration = 500
                start()
            }
            ObjectAnimator.ofFloat(binding.fabShare,"translationY", 0f ).apply {
                duration = 500
                start()
            }
        } else{
            ObjectAnimator.ofFloat(binding.fab, View.ROTATION, 0f, 45f ).apply {
                duration = 500
                start()
            }
            ObjectAnimator.ofFloat(binding.fabMail,"translationY", -300f ).apply {
                duration = 500
                start()
            }
            ObjectAnimator.ofFloat(binding.fabShare,"translationY", -600f ).apply {
                duration = 500
                start()
            }
        }

        isOpen = !isOpen
    }
}
