package com.ssafy.ws_android_component_03_3_hojojeong.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.ssafy.ws_android_component_03_3_hojojeong.dto.StuffDto

private const val TAG = "StuffDao_싸피"
class StuffDao(context: Context){
    private val DB_NAME = "ssafy_stuff.db"
    private var helper : DBHelper =  DBHelper(context, DB_NAME, null, 1)

    fun insert(stuffItem : StuffDto) : Long{
        return helper.insert(stuffItem)
        Log.d(TAG, "insert: ")
    }
    
    fun update(stuffItem: StuffDto) : Int{
        return helper.update(stuffItem)
        Log.d(TAG, "update: ")
    }
    
    fun delete(id : Int) : Int{
        return helper.delete(id)
        Log.d(TAG, "delete: ")
    }
    
    fun selectById(id : Int) : StuffDto?{
        return helper.selectById(id)
        Log.d(TAG, "selectById: ")
    }
    
    fun selectAll() : MutableList<StuffDto> {
        return helper.selectAll()
        Log.d(TAG, "selectAll: ")
    }
}