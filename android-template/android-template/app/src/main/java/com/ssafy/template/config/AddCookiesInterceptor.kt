package com.ssafy.template.config

import android.util.Log
import com.ssafy.template.config.ApplicationClass.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

private const val TAG = "AddCoIncep_싸피"
class AddCookiesInterceptor : Interceptor{

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        // cookie 가져오기
        val getCookies = sSharedPreferences.getUserCookie()
        for (cookie in getCookies!!) {
            builder.addHeader("Cookie", cookie)
            Log.d(TAG,"Adding Header: $cookie")
        }
        return chain.proceed(builder.build())
    }
}