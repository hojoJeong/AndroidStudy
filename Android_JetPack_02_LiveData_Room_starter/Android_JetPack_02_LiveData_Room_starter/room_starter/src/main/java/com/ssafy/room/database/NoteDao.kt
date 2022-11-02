package com.ssafy.room.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    suspend fun getNotes() : MutableList<NotesDto>

    @Query("SELECT * FROM note WHERE id = (:id)")
    suspend fun getNote(id: Long) : NotesDto

    @Insert(onConflict = REPLACE)
    suspend fun insertNote(dto : NotesDto)

    @Update
    suspend fun updateNote(dto : NotesDto)

    @Delete/*("DELETE FROM note where id = (:id)")*/
    suspend fun deleteNote(dto : NotesDto)
}


