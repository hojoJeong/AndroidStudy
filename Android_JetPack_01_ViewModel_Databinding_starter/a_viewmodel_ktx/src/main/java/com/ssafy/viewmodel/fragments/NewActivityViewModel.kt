package com.ssafy.viewmodel.fragments

import androidx.lifecycle.ViewModel

class NewActivityViewModel: ViewModel() {
    var count = 0
        private set

    fun increaseCount(){
        count++
    }
    fun decreaseCount(){
        count--
    }

}