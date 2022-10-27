package com.ssafy.b_coroutine_scope

import kotlinx.coroutines.*


fun main() = runBlocking {
    doSomething()
    println("Done")
}

private suspend fun doSomething() = coroutineScope {

    val one = async {
        delay(1000L)
        "result ONE"
    }
    val two = async {
        delay(2000L)
        "result TWO"
    }

    launch {
        println("test")
        val result = "one : ${one.await()} , two : ${ two.await()}"
        println(result)
    }
}