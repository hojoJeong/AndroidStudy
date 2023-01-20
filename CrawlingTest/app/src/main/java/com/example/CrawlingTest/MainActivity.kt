package com.example.CrawlingTest

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.CrawlingTest.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity() {
    lateinit var doc : org.jsoup.nodes.Document
    var number: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val coroutine = CoroutineScope(Dispatchers.Main)
        coroutine.launch {
            getLottoNum()
            binding.tvLotto.text = number
        }

    }

    suspend fun getLottoNum() = withContext(Dispatchers.IO){
            try{
                doc = Jsoup.connect("https://dhlottery.co.kr/gameResult.do?method=byWin").get()
                var contents : Elements? = doc.select(".win_result").select("h4")
                number = contents?.text() + " "

                for(i in 1..5){
                    contents = doc.select(".ball_645.lrg.ball$i")
                    Log.d(TAG, "getLottoNum: $contents")

                    number += " ${contents.text()}"
                }
                Log.d(TAG, "onCreate 복권 번호: $number")

            } catch (e : java.lang.Exception){
                e.printStackTrace()
            }
    }
}