package com.ssafy.room.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NotesDto::class], version = 1)
abstract class NoteDatabase :RoomDatabase(){

    abstract fun noteDao() : NoteDao

}


