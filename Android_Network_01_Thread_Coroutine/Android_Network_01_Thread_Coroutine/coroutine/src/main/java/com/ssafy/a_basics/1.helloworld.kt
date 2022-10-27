package com.ssafy.coroutine.sample

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun main() {
    GlobalScope.launch {
        delay(300L)
        println("World!")
    }

    println("Hello,")
    Thread.sleep(500L)
}
