package com.ssafy.coroutine.sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    val job = launch {
        delay(300L)
        println("World!")
    }

    println("Hello,")
    job.join()
    println("Done.")
}