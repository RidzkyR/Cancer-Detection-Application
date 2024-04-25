package com.dicoding.asclepius.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class HistoryResults (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "imageUri")
    var imageUri: String?,

    @ColumnInfo(name = "category")
    var category: String? = null,

    @ColumnInfo(name = "score")
    var score: Float = 0F,

    @ColumnInfo(name = "inferenceTime")
    var inferenceTime: String? = null,
): Parcelable