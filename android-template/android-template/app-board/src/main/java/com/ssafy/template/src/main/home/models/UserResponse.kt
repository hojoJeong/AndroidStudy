package com.ssafy.template.src.main.home.models

import com.google.gson.annotations.SerializedName
import com.ssafy.template.config.BaseResponse

//BaseResponse 상속
data class UserResponse(
    @SerializedName("result") val result: ArrayList<ResultUser>
) : BaseResponse()
