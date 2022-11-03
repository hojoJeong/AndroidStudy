package com.ssafy.jetpackall

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ssafy.jetpackall.database.NotesDto
import com.ssafy.jetpackall.repository.NoteRepository

class NoteListViewModel : ViewModel() {

    private var repository = NoteRepository.get()

    val noteList: LiveData<MutableList<NotesDto>> = repository.getNotes()

    fun note(id:Long): LiveData<NotesDto> {
        return repository.getNote(id)
    }
}