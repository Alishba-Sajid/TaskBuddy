package com.example.mytodoapp.domain.repository

import com.example.mytodoapp.domain.model.Task
import kotlinx.coroutines.flow.Flow
interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getTaskById(taskId: Long): Flow<Task?>
    suspend fun insertTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}