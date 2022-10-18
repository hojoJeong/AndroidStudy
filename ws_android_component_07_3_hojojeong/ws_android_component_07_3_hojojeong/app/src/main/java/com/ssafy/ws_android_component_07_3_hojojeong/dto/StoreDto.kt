package com.ssafy.ws_android_component_07_3_hojojeong.dto

import java.io.Serializable

data class StoreDto(
    var title: String = "",
    var number: String = "",
    var lat: String = "",
    var long: String = ""
) : Serializable