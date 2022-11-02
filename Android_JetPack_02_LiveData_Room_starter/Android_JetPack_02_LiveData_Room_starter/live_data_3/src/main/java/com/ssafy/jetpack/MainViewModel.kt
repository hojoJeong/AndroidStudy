package com.ssafy.jetpack

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _elapsedTime = TimerLiveData()

    fun getElapsedTime() = _elapsedTime
}
