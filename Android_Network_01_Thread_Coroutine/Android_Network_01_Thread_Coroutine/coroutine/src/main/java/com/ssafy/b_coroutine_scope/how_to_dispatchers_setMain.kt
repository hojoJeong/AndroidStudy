package com.ssafy.b_coroutine_scope

import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.coroutineContext

fun main() {
    runBlocking {
        test()
    }
}

suspend fun test(){
    // 테스트코드에서는 Dispatchers를 setMain 한 후 사용한다.
    // https://developer.android.com/kotlin/coroutines/coroutines-best-practices?hl=ko
    Dispatchers.setMain(coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)

    // Scope객체 할당
    val scope = CoroutineScope(Dispatchers.Main)

    // Dispatchers.Main으로 작업 진행.
    scope.launch {
        // 코루틴 block
    }

    // CoroutineContext를 Dispatchers.IO로 재정의
    scope.launch(Dispatchers.IO) {
        // 코루틴 block
    }

}