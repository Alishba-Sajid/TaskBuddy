package com.example.mytodoapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.mytodoapp.domain.model.Task

interface TaskRepository {

    fun getAllTasks(): LiveData<List<Task>>

    suspend fun insertTask(task: Task): Long

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)

}
