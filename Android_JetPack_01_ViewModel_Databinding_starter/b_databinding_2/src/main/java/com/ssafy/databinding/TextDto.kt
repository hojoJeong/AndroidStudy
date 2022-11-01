package com.ssafy.databinding

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

private const val TAG = "TextDto_싸피"
class TextDto : BaseObservable(){

    @get:Bindable
    var name: String = "Hello"
        set(value) {
            field = value
            Log.d(TAG, "TextDto : $value")
            notifyPropertyChanged(BR.name)
        }
}