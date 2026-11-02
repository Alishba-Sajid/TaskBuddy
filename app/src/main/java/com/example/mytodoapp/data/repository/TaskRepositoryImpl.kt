
package com.example.mytodoapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.mytodoapp.data.local.Task as TaskEntity
import com.example.mytodoapp.data.local.TaskDao
import com.example.mytodoapp.domain.model.Task as DomainTask
import com.example.mytodoapp.domain.repository.TaskRepository


class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): LiveData<List<DomainTask>> {
        return taskDao.getAllTasks().map { taskEntities ->
            taskEntities.map { entity ->
                entity.toDomain()
            }
        }
    }


    override suspend fun insertTask(task: DomainTask): Long {
        return taskDao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: DomainTask) {
        taskDao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: DomainTask) {
        taskDao.deleteTask(task.toEntity())
    }

    private fun TaskEntity.toDomain(): DomainTask {
        return DomainTask(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted,
            createdAt = createdAt
        )
    }

    private fun DomainTask.toEntity(): TaskEntity {
        return TaskEntity(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted,
            createdAt = createdAt
        )
    }
}
