package com.ssafy.ws_android_component_04_3_hojojeong.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ssafy.ws_android_component_04_3_hojojeong.dao.StuffDao
import com.ssafy.ws_android_component_04_3_hojojeong.dto.StuffDto

private const val TAG = "BoundService_싸피"

class BoundService : Service() {
    private val mBinder: IBinder = MyLocalBinder()

    private val stuffDao by lazy {
        StuffDao(this)
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    fun insert(stuffItem: StuffDto): Long {
        return stuffDao.insert(stuffItem)
    }

    fun update(stuffItem: StuffDto): Int {
        return stuffDao.update(stuffItem)
    }

    fun delete(id: Int): Int {
        return stuffDao.delete(id)

    }

    fun selecById(id: Int): StuffDto? {
        return stuffDao.selectById(id)
    }

    fun selectAll(): MutableList<StuffDto> {
        return stuffDao.selectAll()
    }

    inner class MyLocalBinder : Binder() {
        fun getService(): BoundService = this@BoundService
    }
}