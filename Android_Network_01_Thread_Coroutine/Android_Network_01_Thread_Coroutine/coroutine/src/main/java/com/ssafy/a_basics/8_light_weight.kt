package com.ssafy.coroutine.sample3

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

private const val TAG = "8_light_weight_싸피"
//sampleStart
fun main()  {
    runBlocking {
        repeat(100_000) { // launch a lot of coroutines
            launch {
                delay(5000L)
                print(".")
            }
        }
    }

//        repeat(100_000) { // launch a lot of coroutines
//            thread {
//                Thread.sleep(1000L)
//                print(".")
//            }
//        }
//    }

    var before = System.currentTimeMillis()
    repeat(100_000) { // launch a lot of coroutines
        thread {
            Thread.sleep(5000L)
            print(".")
        }
    }

    var after = System.currentTimeMillis()
    Log.d(TAG, "걸린 시간 : ${after - before}.ms")
}

//sampleEnd

