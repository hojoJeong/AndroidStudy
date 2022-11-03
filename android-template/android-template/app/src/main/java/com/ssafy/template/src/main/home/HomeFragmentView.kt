package com.ssafy.template.src.main.home

import com.ssafy.template.src.main.home.models.SignUpResponse
import com.ssafy.template.src.main.home.models.UserResponse

interface HomeFragmentView {

    fun onGetUserSuccess(response: UserResponse)

    fun onGetUserFailure(message: String)

    fun onPostSignUpSuccess(response: SignUpResponse)

    fun onPostSignUpFailure(message: String)
}