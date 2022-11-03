package com.ssafy.template.src.main.home

import android.util.Log
import com.ssafy.template.config.ApplicationClass
import com.ssafy.template.src.main.home.models.PostSignUpRequest
import com.ssafy.template.src.main.home.models.SignUpResponse
import com.ssafy.template.src.main.home.models.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "HomeService_싸피"
class HomeService(val view: HomeFragmentView) {

    fun tryGetUsers(){
//        CoroutineScope(Dispatchers.Main).launch{
//            val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
//            var response = homeRetrofitInterface.getUsers()
//            Log.d(TAG, "tryGetUsers: ${response}")
//            view.onGetUserSuccess(response)
//
//        }

        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getUsers().enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d(TAG, "onResponse: ${response.body()}")
                view.onGetUserSuccess(response.body() as UserResponse)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                view.onGetUserFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostSignUp(postSignUpRequest: PostSignUpRequest){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.postSignUp(postSignUpRequest).enqueue(object : Callback<SignUpResponse>{
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                view.onPostSignUpSuccess(response.body() as SignUpResponse)
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                view.onPostSignUpFailure(t.message ?: "통신 오류")
            }
        })
    }

}
