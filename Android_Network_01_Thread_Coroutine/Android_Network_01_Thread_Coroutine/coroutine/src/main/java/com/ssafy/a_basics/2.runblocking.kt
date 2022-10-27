package com.ssafy.coroutine.sample

import kotlinx.coroutines.*


fun main() {
    GlobalScope.launch {
        delay(300L)
        println("World!")
    }

    println("Hello,")
    runBlocking {
        delay(500L)
    }
}


