package com.ssafy.b_coroutine_scope

import kotlinx.coroutines.*

fun main() = runBlocking{
    println("Start : before withContext")
    val time = withContext(Dispatchers.Default) {
        println("Start withContext")
        delay(3000)
        "after 3 seconds"
    }

    println("Start : after withContext")
    println("Result : $time")
}