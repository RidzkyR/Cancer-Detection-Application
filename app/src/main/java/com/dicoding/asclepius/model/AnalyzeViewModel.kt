package com.dicoding.asclepius.model

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.database.Analyze
import com.dicoding.asclepius.repository.AnalyzeRepository

class AnalyzeViewModel(application: Application) : ViewModel() {
    private val mAnalyzeRepository: AnalyzeRepository = AnalyzeRepository(application)
    fun insert(analyze: Analyze){
        mAnalyzeRepository.insert(analyze)
    }
}