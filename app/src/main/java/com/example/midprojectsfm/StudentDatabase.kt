// StudentDatabase.kt
package com.example.midprojectsfm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ActivityModel::class], version = 1, exportSchema = false)

abstract class StudentDatabase : RoomDatabase() {

    abstract fun activityDao(): ActivityDao

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getDatabase(context: Context): StudentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "student_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
