package com.ssafy.template.src.main.home.models

import com.google.gson.annotations.SerializedName
import com.ssafy.template.config.BaseResponse

data class SignUpResponse(
    @SerializedName("result") val result: ResultUser
) : BaseResponse()