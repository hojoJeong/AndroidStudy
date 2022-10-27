package com.ssafy.network_2.board

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 앱이 실행될 때 1번만 실행이 됨
class ApplicationClass : Application() {

    //url must be end with "/"
    val BOARD_URL   = "http://192.168.33.128:9999/vue/"

    companion object {
        // 전역변수 문법을 통해 Retrofit 인스턴스를 앱 실행 시 1번만 생성하여 사용 (싱글톤 객체)
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        // 앱이 처음 생성되는 순간, retrofit 인스턴스를 생성
        retrofit = Retrofit.Builder()
                .baseUrl(BOARD_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}