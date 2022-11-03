package com.ssafy.jetpackall

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ssafy.jetpackall.database.NotesDto
import com.ssafy.jetpackall.repository.NoteRepository

class NoteViewModel(id: Long) : ViewModel() {
    var _id = id
        private set

    val note: LiveData<NotesDto> = NoteRepository.get().getNote(id)
}