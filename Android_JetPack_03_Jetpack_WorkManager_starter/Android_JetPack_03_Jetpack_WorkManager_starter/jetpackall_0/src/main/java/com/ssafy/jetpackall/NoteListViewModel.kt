package com.ssafy.jetpackall

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ssafy.jetpackall.database.NotesDto
import com.ssafy.jetpackall.repository.NoteRepository

class NoteListViewModel : ViewModel() {

    val noteList: LiveData<MutableList<NotesDto>> = NoteRepository.get().getNotes()

    fun note(id:Long) : LiveData<NotesDto> {
        return NoteRepository.get().getNote(id)
    }
}