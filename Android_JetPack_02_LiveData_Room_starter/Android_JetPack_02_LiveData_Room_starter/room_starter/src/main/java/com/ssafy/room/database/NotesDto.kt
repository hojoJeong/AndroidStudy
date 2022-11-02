package com.ssafy.room.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "note")
data class NotesDto(@NotNull val TITLE:String= "title", val BODY:String="body") {

    @PrimaryKey(autoGenerate = true)
    var ID: Long = 0

    constructor(id:Long, title:String, content:String): this(title, content){
        this.ID = id;
    }
}