package com.android.example.suspend

import kotlinx.coroutines.*


fun main() = runBlocking {
    val job = GlobalScope.launch {
        repeat(2) { times ->
            longRunningTask(times, times + 1)
        }
    }
    job.join()
}

suspend fun longRunningTask(input1: Int, input2: Int): Int {
    delay(2000)
    println("Intermediate result has been calculated. input1: $input1, input2:$input2")
    delay(2000)
    val result = input1 + input2
    println("All of the calculation process have done. : $result")
    return result
}