package com.ssafy.googlemap.weather


import com.ssafy.googlemap.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {

    @GET("weather")
    fun getWeather(@Query("lat") lat : String,
                   @Query("lon") lon : String,
                   @Query("appid") appid : String
    ) : Call<WeatherResponse>
}