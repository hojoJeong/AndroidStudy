package com.android.example.suspend

import kotlinx.coroutines.*


fun main() = runBlocking{
    var test ="test"

    //Dispatcher.Main은 확장이 안됨.  오류 : Module with the Main dispatcher had failed to initialize
    CoroutineScope(Dispatchers.IO).launch {
        loadData { item ->
            println("HELLO : " + item)
            test = "abcd"
        }
    }
    delay(500L)
    println(test)
}


suspend fun CoroutineScope.loadData( body: suspend CoroutineScope.(item: String) -> Unit) {
    delay(100L)
    body("hello")
}