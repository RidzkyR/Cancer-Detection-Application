package com.dicoding.asclepius.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.response.ArticlesItem
import com.dicoding.asclepius.data.response.NewsResponse
import com.dicoding.asclepius.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    private val _listNews = MutableLiveData<List<ArticlesItem>>()
    val listNews: MutableLiveData<List<ArticlesItem>> = _listNews

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        showNews()
    }

    private fun showNews() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getNews(QUERY, API_KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>,
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && response.body() != null) {
                    _listNews.value =
                        (responseBody?.articles?.filterNot { it.title == "[Removed]" })
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "NewsActivity"
        private const val QUERY = "cancer"
        private const val API_KEY = "b4ab6900c9ff4e139a4678cddb5ad82e"
    }
}