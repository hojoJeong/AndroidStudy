package com.ssafy.viewmodel

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var count = 0
        private set

    //화면에서 증가
    fun increaseCount(){
        count++
    }
}