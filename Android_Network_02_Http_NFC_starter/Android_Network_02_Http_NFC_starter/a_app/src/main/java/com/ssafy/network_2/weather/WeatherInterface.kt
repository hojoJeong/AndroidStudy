package com.ssafy.network_2.weather

import com.ssafy.network_2.weather.models.WeatherResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherInterface {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") q: String,
        @Query("appid") appid: String
    ) : WeatherResponse
}