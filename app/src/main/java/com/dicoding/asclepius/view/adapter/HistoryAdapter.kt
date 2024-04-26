package com.dicoding.asclepius.view.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.local.History
import com.dicoding.asclepius.databinding.ItemHistoryBinding
import com.dicoding.asclepius.helper.DiffCallBack
import com.dicoding.asclepius.model.HistoryViewModel
import java.text.NumberFormat

class HistoryAdapter(private val historyViewModel: HistoryViewModel) : RecyclerView.Adapter<HistoryAdapter.AnalizeViewHolder>() {
    private val listHistory = ArrayList<History>()

    fun setListAnalize(listHistory: List<History>) {
        val diffCallback = DiffCallBack(this.listHistory, listHistory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listHistory.clear()
        this.listHistory.addAll(listHistory)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalizeViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnalizeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    override fun onBindViewHolder(holder: AnalizeViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }

    inner class AnalizeViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(history: History) {
            val score = NumberFormat.getPercentInstance().format(history.score).toString()
            with(binding) {
                ivImage.setImageURI(Uri.parse(history.imageUri))
                tvCategory.text = "Result : ${history.category} (${score})"
                tvInferenceTime.text = "InferenceTime : ${history.inferenceTime}"
                tvDate.text = "Created at : ${history.date}s"

                deleteButton.setOnClickListener {
                    historyViewModel.delete(history)
                }
            }
        }
    }

}