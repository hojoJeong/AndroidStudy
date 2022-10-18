package com.ssafy.ws_android_component_07_3_hojojeong.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ssafy.ws_android_component_07_3_hojojeong.dto.StuffDto

private const val TAG = "DBHelper_싸피"
class DBHelper(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    private val db = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "onCreate: ")
        db.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(TABLE_DELETE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(TABLE_DELETE)
        onCreate(db)
    }

    fun insert(stuffItem: StuffDto): Long {
        val contentValue = ContentValues().apply {
            put(STUFF_NAME, stuffItem.stuffName)
            put(STUFF_CNT, stuffItem.quantity)
            put(STUFF_REGDATE, stuffItem.regDate)
        }
        db.beginTransaction()
        val result = db.insert(TABLE, null, contentValue)
        if (result > 0) db.setTransactionSuccessful()
        db.endTransaction()

        return result
    }

    fun update(stuffItem: StuffDto): Int {
        val contentValue = ContentValues().apply {
            put(STUFF_NAME, stuffItem.stuffName)
            put(STUFF_CNT, stuffItem.quantity)
            put(STUFF_REGDATE, stuffItem.regDate)
        }
        Log.d(TAG, "update씨: $stuffItem")
        db.beginTransaction()
        val result = db.update(TABLE, contentValue, "_id=?", arrayOf(stuffItem._id.toString()))
        if (result > 0) db.setTransactionSuccessful()
        db.endTransaction()

        return result
    }

    fun delete(id: Int): Int {
        db.beginTransaction()
        val result = db.delete(TABLE, "_id=?", arrayOf(id.toString()))
        if (result > 0) db.setTransactionSuccessful()
        db.endTransaction()

        return result
    }

    fun selectAll(): MutableList<StuffDto> {
        val stuffList = mutableListOf<StuffDto>()

        db.rawQuery("select * from $TABLE", null).use {
            while (it.moveToNext()) {
                val id = it.getInt(0)
                val stuffItem = it.getString(1)
                val quantity = it.getInt(2)
                val regDate = it.getLong(3)

                stuffList.add(StuffDto(id, stuffItem, quantity, regDate))
            }
        }
        return stuffList
    }

    fun selectById(getId: Int): StuffDto? {
        var stuffData: StuffDto? = null

        db.rawQuery("select * from $TABLE where _id = ?", arrayOf(getId.toString())).use {
            while (it.moveToNext()) {
                val id = it.getInt(0)
                val stuffItem = it.getString(1)
                val quantity = it.getInt(2)
                val regDate = it.getLong(3)

                stuffData = StuffDto(id, stuffItem, quantity, regDate)
            }
        }
        return stuffData
    }

    companion object {
        private const val TABLE_NAME = "Stuff"
        private const val STUFF_ID = "_id"
        private const val STUFF_NAME = "stuffName"
        private const val STUFF_CNT = "quantity"
        private const val STUFF_REGDATE = "regDate"
        private const val TABLE = "Stuff"
        private const val TABLE_DELETE = "DROP TABLE if exists $TABLE;"
        private const val TABLE_CREATE = "CREATE TABLE if not exists $TABLE " +
                "(_id integer primary key autoincrement, stuffName text, quantity integer, regDate integer);"
    }
}

