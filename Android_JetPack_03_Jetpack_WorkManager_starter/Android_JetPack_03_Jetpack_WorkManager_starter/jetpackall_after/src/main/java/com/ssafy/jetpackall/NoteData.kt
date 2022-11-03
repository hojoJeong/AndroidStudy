package com.ssafy.jetpackall

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.ssafy.jetpackall.database.NotesDto

class NoteData:BaseObservable() {
    var note: NotesDto? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val title: String?
        get() = note?.TITLE
}