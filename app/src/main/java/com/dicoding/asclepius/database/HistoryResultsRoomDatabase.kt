package com.dicoding.asclepius.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryResults::class], version = 1)
abstract class HistoryResultsRoomDatabase : RoomDatabase() {
    abstract fun historyResultsDao() : HistoryResultsDao
    companion object {
        @Volatile
        private var INSTANCE : HistoryResultsRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): HistoryResultsRoomDatabase {
            if (INSTANCE == null) {
                synchronized(HistoryResultsRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        HistoryResultsRoomDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as HistoryResultsRoomDatabase
        }
    }
}