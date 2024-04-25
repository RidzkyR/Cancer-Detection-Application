package com.dicoding.asclepius.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.asclepius.database.Analyze

class DiffCallBack(
    private val oldNoteList: List<Analyze>,
    private val newNoteList: List<Analyze>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].id == newNoteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldData = oldNoteList[oldItemPosition]
        val newData = newNoteList[newItemPosition]
        return oldData.imageUri == newData.imageUri && oldData.category == newData.category && oldData.score == newData.score && oldData.inferenceTime == newData.inferenceTime
    }
}