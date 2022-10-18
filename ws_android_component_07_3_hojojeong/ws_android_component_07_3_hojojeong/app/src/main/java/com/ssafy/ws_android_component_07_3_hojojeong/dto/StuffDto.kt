package com.ssafy.ws_android_component_07_3_hojojeong.dto

import android.util.Log
import java.text.SimpleDateFormat

data class StuffDto(
    val stuffName: String,
    val quantity: Int,
    val regDate: Long
) : java.io.Serializable {
    var _id :Int = -1

    constructor(_id : Int, stuffName: String, quantity: Int, regDate: Long): this(stuffName, quantity, regDate) {
        this._id = _id
    }

    override fun toString(): String {
        val dateFormat = SimpleDateFormat("yy / MM / dd").format(regDate)
        return "물품 : $stuffName -> 수량 : $quantity, 입고일 : $dateFormat"
    }
}