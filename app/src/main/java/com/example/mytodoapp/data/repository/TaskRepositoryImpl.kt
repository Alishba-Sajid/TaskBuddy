
package com.example.mytodoapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.mytodoapp.data.local.database.TaskDao
import com.example.mytodoapp.data.local.database.toDomain
import com.example.mytodoapp.data.local.database.toEntity
import com.example.mytodoapp.domain.model.Task
import com.example.mytodoapp.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): LiveData<List<Task>> {
        return taskDao.getAllTasks().map { taskEntities ->
            taskEntities.map { entity ->
                entity.toDomain()
            }
        }
    }

    override fun getTaskById(taskId: Long): LiveData<Task?> {
        return taskDao.getTaskById(taskId).map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun insertTask(task: Task): Long {
        return taskDao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())
    }
}
