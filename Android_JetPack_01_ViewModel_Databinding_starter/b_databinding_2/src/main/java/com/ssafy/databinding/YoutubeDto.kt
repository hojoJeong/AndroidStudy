package com.ssafy.databinding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class YoutubeDto(val image: Drawable, val title: String) : BaseObservable() {

    @get:Bindable
    var isClicked: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.clicked)  // BR : Binding된 Resource ID가 관리되는 Class
        }

    fun onClickListener(view: View) {
        Toast.makeText(view.context, "Clicked: $title", Toast.LENGTH_SHORT).show()
        isClicked = when(isClicked){
            false -> true
            true -> false
        }
    }
}