package com.ssafy.ws_android_component_01_3_hojojeong.dto

class Stuff : java.io.Serializable{
    var title: String = ""
    var number: String = ""
    var lat: String = ""
    var long: String = ""
    var stuffName: String = ""
    var quantity: Int = 0

    constructor(stuffName: String, quantity: Int){
        this.stuffName = stuffName
        this.quantity = quantity
    }
    constructor(title: String, number: String, lat: String, long: String){
        this.title = title
        this.number = number
        this.lat = lat
        this.long = long
    }

    override fun toString(): String {
        return "물품 : $stuffName -> 수량 : $quantity"
    }


}