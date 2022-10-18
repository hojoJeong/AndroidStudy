package com.ssafy.ws_android_component_02_3_hojojeong.dto

data class StuffDto(
    var stuffName: String = "",
    var quantity: Int = 0
) : java.io.Serializable {

    override fun toString(): String {
        return "물품 : $stuffName -> 수량 : $quantity"
    }

}