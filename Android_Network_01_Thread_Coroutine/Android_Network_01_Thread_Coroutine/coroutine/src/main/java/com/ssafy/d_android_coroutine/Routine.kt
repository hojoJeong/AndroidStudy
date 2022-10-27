package com.ssafy.d_android_coroutine

import kotlinx.coroutines.*


var currentIndex = 0

fun main() = runBlocking{
    10.countDown(++currentIndex)
}

private suspend fun Int.countDown(currentIndex: Int) {
    for (index in this downTo 1) {
        println("Now index $currentIndex Countdown $index")
        delay(200)
    }
//    Log.i("TEMP", "Now index $currentIndex Done!")
}

