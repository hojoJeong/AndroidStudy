package com.ssafy.palette_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import com.ssafy.palette_1.databinding.ActivityRatingBarBinding

class RatingBarActivity : AppCompatActivity() {

    lateinit var binding : ActivityRatingBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ratingBarDefault.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                binding.ratingBarMiddle.rating = rating
                binding.ratingBarSmall.rating = rating
            }

        binding.button.setOnClickListener {
            startActivity(Intent(this, SearchViewActivity::class.java))
        }
    }
}