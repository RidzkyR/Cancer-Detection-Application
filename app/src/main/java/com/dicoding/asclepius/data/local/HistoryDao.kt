package com.dicoding.asclepius.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: History)

    @Delete
    fun delete(history: History)

    @Query("SELECT * FROM analiyze ORDER BY id ASC")
    fun getAllData(): LiveData<List<History>>

}