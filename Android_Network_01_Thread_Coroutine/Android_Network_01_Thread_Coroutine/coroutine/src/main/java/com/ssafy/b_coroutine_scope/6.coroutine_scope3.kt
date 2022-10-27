package com.ssafy.b_coroutine_scope3

import kotlinx.coroutines.*


fun main(): Unit = runBlocking {
    val job = launch(CoroutineName("Parent")) {
        println("Before")
        longTask()
        println("After")
    }
    delay(1500)
    job.cancel()
}

suspend fun longTask() = coroutineScope {
    launch {
        delay(1000)
        val name = coroutineContext[CoroutineName]?.name
        println("[$name] Finished task 1")
    }

    launch {
        delay(2000)
        val name = coroutineContext[CoroutineName]?.name
        println("[$name] Finished task 2")
    }
}
