package com.ssafy.template.src.main.home.models

import com.google.gson.annotations.SerializedName

data class ResultUser(
    @SerializedName("userId") val userId: String,
    @SerializedName("name") val name: String
)
