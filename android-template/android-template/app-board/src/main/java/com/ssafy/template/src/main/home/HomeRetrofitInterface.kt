package com.ssafy.template.src.main.home

import com.ssafy.template.src.main.home.models.PostSignUpRequest
import com.ssafy.template.src.main.home.models.SignUpResponse
import com.ssafy.template.src.main.home.models.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeRetrofitInterface {
    @GET("api/user/users")
    fun getUsers() : Call<UserResponse>

    @POST("api/user/login")
    fun postSignUp(@Body params: PostSignUpRequest): Call<SignUpResponse>
}
