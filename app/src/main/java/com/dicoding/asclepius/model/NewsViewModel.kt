package com.dicoding.asclepius.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.response.ArticlesItem

class NewsViewModel : ViewModel() {
    private val _restaurant = MutableLiveData<ArticlesItem>()
    val restaurant: LiveData<ArticlesItem> = _restaurant

    private val _listReview = MutableLiveData<List<ArticlesItem>>()
    val listReview: LiveData<List<ArticlesItem>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
}