package com.dicoding.asclepius.view.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.response.ArticlesItem
import com.dicoding.asclepius.data.response.NewsResponse
import com.dicoding.asclepius.data.retrofit.ApiConfig
import com.dicoding.asclepius.databinding.ActivityNewsBinding
import com.dicoding.asclepius.view.adapter.NewsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvNews.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvNews.addItemDecoration(itemDecoration)

        showNews()
    }

    private fun showNews() {
        showLoading(true)
        val client = ApiConfig.getApiService().getNews(QUERY, API_KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                showLoading(false)
                val responseBody = response.body()
                if (response.isSuccessful && response.body() != null) {
                    setNewsData(responseBody?.articles?.filterNot { it.title == "[Removed]" })
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                showLoading(false)
            }
        })
    }

    private fun setNewsData(newsData: List<ArticlesItem>?) {
        val adapter = NewsAdapter()
        adapter.submitList(newsData)
        binding.rvNews.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        private const val QUERY = "cancer"
        private const val API_KEY = "b4ab6900c9ff4e139a4678cddb5ad82e"
    }
}