package com.ssafy.b_coroutine_scope

import kotlinx.coroutines.*


fun main() = runBlocking {
    var job = CoroutineScope(Dispatchers.Default).launch {
        delay(1000)
        println("test")
    }

    println("Start")
    job.join()
    println("Done")
}