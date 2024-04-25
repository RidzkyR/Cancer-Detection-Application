package com.dicoding.asclepius.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.database.Analyze
import com.dicoding.asclepius.repository.AnalyzeRepository

class HistoryViewModel(application: Application) : ViewModel() {
    private val mAnalyzeRepository: AnalyzeRepository = AnalyzeRepository(application)
    fun getAllData(): LiveData<List<Analyze>> = mAnalyzeRepository.getAllData()
}