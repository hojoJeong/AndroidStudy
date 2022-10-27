package com.ssafy.b_coroutine_scope1

import kotlinx.coroutines.*

fun main(): Unit = runBlocking {
    val job = CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
        println("CoroutineScope.launch Lazy Start")
    }

    val deferred = async(start = CoroutineStart.LAZY) {
        println("async Lazy Start")
        "async Lazy Result"
    }

    println("Start")

//    job.start()		// launch는 start() 또는 join()으로 해당 코루틴을 실행
     job.join()	// start()와 동일하게 실행시킴. join은 결과 대기까지

//    val result = deferred.start()	// async는 start() 또는 await()으로 해당 코루틴을 실행
                                    // start()는 결과를 대기하지 않으므로 실행결과인 true를 리턴
   val result =  deferred.await()	// await는 실행시키고 대기까지

    println("result : $result")
}