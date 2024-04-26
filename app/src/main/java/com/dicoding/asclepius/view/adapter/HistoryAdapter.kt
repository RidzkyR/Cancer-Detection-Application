package com.dicoding.asclepius.view.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.database.Analyze
import com.dicoding.asclepius.databinding.ItemHistoryBinding
import com.dicoding.asclepius.helper.DiffCallBack
import java.text.NumberFormat

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.AnalizeViewHolder>() {
    private val listAnalyze = ArrayList<Analyze>()

    fun setListAnalize(listAnalyze: List<Analyze>) {
        val diffCallback = DiffCallBack(this.listAnalyze, listAnalyze)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listAnalyze.clear()
        this.listAnalyze.addAll(listAnalyze)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalizeViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnalizeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listAnalyze.size
    }

    override fun onBindViewHolder(holder: AnalizeViewHolder, position: Int) {
        holder.bind(listAnalyze[position])
    }

    inner class AnalizeViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(analyze: Analyze) {
            val score = NumberFormat.getPercentInstance().format(analyze.score).toString()
            with(binding) {
                ivImage.setImageURI(Uri.parse(analyze.imageUri))
                tvCategory.text = "Result : ${analyze.category} (${score})"
                tvInferenceTime.text = "InferenceTime : ${analyze.inferenceTime}"
                tvDate.text = "Created at : ${analyze.date}s"
            }
        }
    }

}