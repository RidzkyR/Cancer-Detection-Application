package com.dicoding.asclepius.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Analyze::class], version = 1)
abstract class AnalyzeRoomDatabase : RoomDatabase() {
    abstract fun historyResultsDao(): AnalyzeDao

    companion object {
        @Volatile
        private var INSTANCE: AnalyzeRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AnalyzeRoomDatabase {
            if (INSTANCE == null) {
                synchronized(AnalyzeRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AnalyzeRoomDatabase::class.java, "history_database"
                    )
                        .build()
                }
            }
            return INSTANCE as AnalyzeRoomDatabase
        }
    }
}