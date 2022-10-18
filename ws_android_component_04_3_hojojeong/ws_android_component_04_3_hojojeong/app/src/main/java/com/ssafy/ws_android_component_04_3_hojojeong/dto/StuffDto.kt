package com.ssafy.ws_android_component_04_3_hojojeong.dto

data class StuffDto(
    var stuffName: String,
    var quantity: Int
) : java.io.Serializable {
    var _id :Int = -1

    constructor(_id : Int, stuffName: String, quantity: Int): this(stuffName, quantity) {
        this._id = _id
    }

    override fun toString(): String {
        return "id: $_id 물품 : $stuffName -> 수량 : $quantity"
    }
}