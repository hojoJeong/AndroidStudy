package com.ssafy.note.sample

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

private const val TAG = "NotesDbAdapter_싸피"
class NotesDbAdapter {

    lateinit var mDbHelper: DatabaseHelper
    lateinit var mDb: SQLiteDatabase

    inner class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            Log.d(TAG, "DatabaseHelper.onCreate...")
            db.execSQL(DATABASE_CREATE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w( TAG, "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data")
            db.execSQL("DROP TABLE IF EXISTS notes")
            onCreate(db)
        }
    }

    fun open(mCtx: Context) {
        Log.d(TAG, "open....")
        mDbHelper = DatabaseHelper(mCtx)
        mDb = mDbHelper.writableDatabase
    }

    fun close() {
        mDbHelper.close()
    }

    fun insertNote(title: String?, body: String?): Long {
        val initialValues = ContentValues()
        initialValues.put(KEY_TITLE, title)
        initialValues.put(KEY_BODY, body)
        return mDb.insert(DATABASE_TABLE, null, initialValues)
    }

    fun updateNote(rowId: Long, title: String?, body: String?): Boolean {
        val args = ContentValues()
        args.put(KEY_TITLE, title)
        args.put(KEY_BODY, body)
        return mDb.update(DATABASE_TABLE, args, "$KEY_ROWID=$rowId", null) > 0
    }

    fun deleteNote(rowId: Long): Boolean {
        return mDb.delete(DATABASE_TABLE, "$KEY_ROWID=$rowId", null) > 0
    }

    fun selectAllNotes(): MutableList<NotesDto>  {
        val list = mutableListOf<NotesDto>()

        mDb.query(
            DATABASE_TABLE, arrayOf(
                KEY_ROWID, KEY_TITLE,
                KEY_BODY
            ), null, null, null, null, null
        ).use{
            if(it.moveToFirst()) {
                do {
                    list.add(NotesDto(it.getLong(0), it.getString(1), it.getString(2)))
                } while (it.moveToNext())
            }
        }
        return list
    }

    fun selectNote(rowId: Long): NotesDto {
        mDb.query(
            true, DATABASE_TABLE, arrayOf(
                KEY_ROWID,
                KEY_TITLE, KEY_BODY
            ), "$KEY_ROWID=$rowId", null,
            null, null, null, null
        ).use {
            it.moveToFirst()
            return NotesDto(it.getLong(0), it.getString(1), it.getString(2))
        }
    }


    companion object {
        const val KEY_TITLE = "title"
        const val KEY_BODY = "body"
        const val KEY_ROWID = "_id"

        private const val DATABASE_CREATE =
            ("create table notes (_id integer primary key autoincrement, title text not null, body text not null);")

        private const val DATABASE_NAME = "data"
        private const val DATABASE_TABLE = "notes"
        private const val DATABASE_VERSION = 1
    }
}