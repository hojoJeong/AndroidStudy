package com.ssafy.jetpack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class NewActivityViewModel : ViewModel() {
    //사용자의 클릭 수를 세는 변수
    private var _count = MutableLiveData<Int>().apply {
        value = 0
    }

    val count: LiveData<Int>
        get() = _count

    //사용자가 클릭 했을 때 클릭수 를 증가시키는 메소드
    fun increaseCount() {
        _count.value = (count.value ?: 0) + 1
    }

    fun decreaseCount() {
        _count.value = (count.value ?: 0) - 1
    }

    //두 개를 곱하기. Livedata의 변형
    val timesText = Transformations.map(count){
        "$it X 2 == ${it*2}"
    }
}