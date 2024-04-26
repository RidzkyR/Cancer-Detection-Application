package com.dicoding.asclepius.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.local.History
import com.dicoding.asclepius.repository.HistoryRepository

class HistoryViewModel(application: Application) : ViewModel() {
    private val mHistoryRepository: HistoryRepository = HistoryRepository(application)
    fun getAllData(): LiveData<List<History>> = mHistoryRepository.getAllData()

    fun insert(history: History) {
        mHistoryRepository.insert(history)
    }

    fun delete(history: History) {
        mHistoryRepository.delete(history)
    }
}