package com.ssafy.template.src.main.home.models

import com.google.gson.annotations.SerializedName

data class PostSignUpRequest(
    @SerializedName("userId") val userId: String,
    @SerializedName("pwd") val password: String
)