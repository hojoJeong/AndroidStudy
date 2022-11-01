package com.ssafy.viewmodel

import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {
    //사용자의 클릭 수를 세는 변수
    var count = 0
        private set

    //사용자가 클릭 했을 때 클릭수 를 증가시키는 메소드
    fun increaseCount(){
        count++
    }

}