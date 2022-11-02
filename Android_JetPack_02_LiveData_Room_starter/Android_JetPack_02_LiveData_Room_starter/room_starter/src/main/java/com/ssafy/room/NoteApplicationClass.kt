package com.ssafy.room

import android.app.Application
import com.ssafy.room.repository.NoteRepository

class NoteApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
    }
}