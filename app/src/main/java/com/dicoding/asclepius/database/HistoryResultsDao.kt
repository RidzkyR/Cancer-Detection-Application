package com.dicoding.asclepius.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryResultsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(historyResults: HistoryResults)

    @Query("SELECT * from historyresults ORDER BY id ASC")
    fun getAllData(): LiveData<List<HistoryResults>>

}