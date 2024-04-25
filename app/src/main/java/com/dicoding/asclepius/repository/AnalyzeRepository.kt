package com.dicoding.asclepius.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.database.Analyze
import com.dicoding.asclepius.database.AnalyzeDao
import com.dicoding.asclepius.database.AnalyzeRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AnalyzeRepository(application: Application) {
    private val mAnalyzeDao: AnalyzeDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = AnalyzeRoomDatabase.getDatabase(application)
        mAnalyzeDao = db.historyResultsDao()
    }

    fun getAllData(): LiveData<List<Analyze>> = mAnalyzeDao.getAllData()
    fun insert(analyze: Analyze) {
        executorService.execute { mAnalyzeDao.insert(analyze) }
    }
}