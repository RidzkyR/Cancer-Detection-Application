package com.dicoding.asclepius.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.database.HistoryResults
import com.dicoding.asclepius.repository.HistoryResultsRepository

class HistoryViewModel(application: Application) : ViewModel() {
    private val mHistoryResultsRepository: HistoryResultsRepository = HistoryResultsRepository(application)
    fun getAllData(): LiveData<List<HistoryResults>> = mHistoryResultsRepository.getAllNotes()
}