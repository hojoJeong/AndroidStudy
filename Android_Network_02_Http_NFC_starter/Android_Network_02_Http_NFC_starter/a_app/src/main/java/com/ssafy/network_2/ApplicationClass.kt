package com.ssafy.network_2

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 앱이 실행될 때 1번만 실행이 됨
class ApplicationClass : Application() {

    val WEATHER_URL = "https://api.openweathermap.org/data/2.5/"

    companion object {

        // 전역변수 문법을 통해 Retrofit 인스턴스를 앱 실행 시 1번만 생성하여 사용 (싱글톤 객체)
        lateinit var wRetrofit : Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        // 앱이 처음 실행되는 순간, retrofit 인스턴스를 생성
        wRetrofit = Retrofit.Builder()
                .baseUrl(WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}