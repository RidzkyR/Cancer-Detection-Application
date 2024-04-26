package com.dicoding.asclepius.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.local.History
import com.dicoding.asclepius.data.local.HistoryDao
import com.dicoding.asclepius.data.local.HistoryRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryRepository(application: Application) {
    private val mHistoryDao: HistoryDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = HistoryRoomDatabase.getDatabase(application)
        mHistoryDao = db.historyResultsDao()
    }

    fun getAllData(): LiveData<List<History>> = mHistoryDao.getAllData()
    fun insert(history: History) {
        executorService.execute { mHistoryDao.insert(history) }
    }

    fun delete(history: History) {
        executorService.execute { mHistoryDao.delete(history) }
    }
}