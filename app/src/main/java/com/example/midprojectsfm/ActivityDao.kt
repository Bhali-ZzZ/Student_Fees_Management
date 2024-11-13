package com.example.midprojectsfm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ActivityDao {
    @Insert
    suspend fun insertStudent(student: ActivityModel)

    @Update
    suspend fun updateStudent(student: ActivityModel)

    @Delete
    suspend fun deleteStudent(student: ActivityModel)

    @Query("SELECT * FROM student_table")
    suspend fun getAllStudents(): List<ActivityModel>
}
