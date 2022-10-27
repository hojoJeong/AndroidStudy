package com.ssafy.b_coroutine_scope

import kotlinx.coroutines.*


//3초 지나면 Exception 발생하는 게 정상, try catch로 예외처리 해줘야함
fun main() = runBlocking{
    println("Start : before withTimout")
    var time = withTimeout(3000) {
                println("Start withTimout")
                delay(5000)
                "after 5 seconds"
            }
    println("Start : after withTimout")
    println("Result : $time")
}