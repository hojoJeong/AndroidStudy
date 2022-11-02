package com.ssafy.jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ssafy.jetpack.databinding.ActivityMainWithFragmentBinding
import kotlin.properties.ReadOnlyProperty


class MainActivityWithFragment : AppCompatActivity() {

    private  val activityWithFragmentViewModel: NewActivityViewModel by viewModels()

    private lateinit var binding: ActivityMainWithFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWithFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}