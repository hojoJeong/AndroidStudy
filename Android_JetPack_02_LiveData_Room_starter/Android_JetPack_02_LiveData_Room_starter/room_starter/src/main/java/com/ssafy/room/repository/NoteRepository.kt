package com.ssafy.room.repository

import android.content.Context
import androidx.room.Room
import androidx.room.withTransaction
import com.ssafy.room.database.NoteDatabase
import com.ssafy.room.database.NotesDto

private const val TAG = "NoteRepository_싸피"
private const val DATABASE_NAME = "note-database.db"
class NoteRepository private constructor(context: Context){

    private val database : NoteDatabase = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val noteDao = database.noteDao()

    suspend fun getNotes() : MutableList<NotesDto> {
        return noteDao.getNotes()
    }

    suspend fun getNote(id : Long) : NotesDto {
        return noteDao.getNote(id)
    }
    suspend fun insertNote(dto: NotesDto) = database.withTransaction{
        noteDao.insertNote(dto)
    }
    suspend fun updateNote(dto: NotesDto) = database.withTransaction{
        noteDao.updateNote(dto)
    }
    suspend fun deleteNote(dto : NotesDto) = database.withTransaction{
        noteDao.deleteNote(dto)
    }

    companion object{
        private var INSTANCE : NoteRepository? =null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = NoteRepository(context)
            }
        }

        fun get() : NoteRepository {
            return INSTANCE ?:
            throw IllegalStateException("NoteRepository must be initialized")
        }
    }

}


