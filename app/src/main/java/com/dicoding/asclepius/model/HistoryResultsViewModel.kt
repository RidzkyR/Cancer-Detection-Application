package com.dicoding.asclepius.model

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.database.HistoryResults
import com.dicoding.asclepius.repository.HistoryResultsRepository

class HistoryResultsViewModel(application: Application) : ViewModel() {
    private val mHistoryResultsRepository: HistoryResultsRepository = HistoryResultsRepository(application)
    fun insert(historyResults: HistoryResults){
        mHistoryResultsRepository.insert(historyResults)
    }
}