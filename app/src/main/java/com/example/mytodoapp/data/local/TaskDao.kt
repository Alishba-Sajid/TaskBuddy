package com.example.mytodoapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {


    @Insert
    suspend fun insertTask(task: Task): Long


    @Query("SELECT * FROM tasks ORDER BY created_at DESC")
    fun getAllTasks(): LiveData<List<Task>>
    @Query("SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    fun getTaskById(taskId: Long): LiveData<Task?>



    @Update
    suspend fun updateTask(task: Task)


    @Delete
    suspend fun deleteTask(task: Task)

}
