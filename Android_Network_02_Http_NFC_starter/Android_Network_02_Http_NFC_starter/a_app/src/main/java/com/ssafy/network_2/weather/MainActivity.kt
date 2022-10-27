package com.ssafy.network_2.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.network_2.ApplicationClass
import com.ssafy.network_2.databinding.ActivityMainBinding
import com.ssafy.network_2.weather.models.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.round

private const val TAG = "MainActivity_싸피"

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val API_KEY = "5cbe9dd4041225479ef6d0e088b2ffb8"  //"OPEN WEATHER MAP API KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tempBtn.setOnClickListener {
            val city = binding.cityEt.text.toString()
            getWeatherData(city, API_KEY)
        }
    }

    private fun getWeatherData(city: String, key: String) {
        val weatherInterface = ApplicationClass.wRetrofit.create(WeatherInterface::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            val result = weatherInterface.getWeather(city, key)
            binding.tempTv.text = "섭씨온도 : ${ result.main.temp.toString()}"
            Log.d(TAG, "getWeatherData 싸피 : $result")
        }
//        weatherInterface.getWeather(city, key).enqueue(object : Callback<WeatherResponse> { //execute호출이 동기
//            override fun onResponse(
//                call: Call<WeatherResponse>,
//                response: Response<WeatherResponse>
//            ) {
//                if (response.isSuccessful){ //response.isSuccessful : 200번대가 다 오는 거
////                if (response.code() == 200) { // response.code 가 200인거
//                    val temp = response.body()?.main?.temp!! - 273.15
////                    binding.tempTv.text = "섭씨온도 : ${round(temp*10)/10}"
//                    binding.tempTv.text = "섭씨온도 : ${"%.2f".format(temp)}"
//                }
//            }
//
//            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
//                Log.e(TAG, "onFailure: 통신오류, ${t.message}")
//            }
//        })
    }
}