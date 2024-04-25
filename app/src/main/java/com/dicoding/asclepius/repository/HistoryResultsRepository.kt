package com.dicoding.asclepius.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.database.HistoryResults
import com.dicoding.asclepius.database.HistoryResultsDao
import com.dicoding.asclepius.database.HistoryResultsRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryResultsRepository(application: Application) {
    private val mHistoryResultsDao: HistoryResultsDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = HistoryResultsRoomDatabase.getDatabase(application)
        mHistoryResultsDao = db.historyResultsDao()
    }

    fun getAllNotes(): LiveData<List<HistoryResults>> = mHistoryResultsDao.getAllData()
    fun insert(historyResults: HistoryResults) {
        executorService.execute { mHistoryResultsDao.insert(historyResults) }
    }
}