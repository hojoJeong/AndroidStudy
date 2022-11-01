package com.ssafy.viewmodel.fragments

interface CommunicationCallback {
    fun increaseCount()
    fun decreaseCount()
    fun getCount():Int
}
