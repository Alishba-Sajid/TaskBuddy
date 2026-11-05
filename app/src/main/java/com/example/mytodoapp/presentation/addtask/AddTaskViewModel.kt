package com.example.mytodoapp.presentation.addtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.domain.model.Task
import com.example.mytodoapp.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    fun getTaskById(taskId: Long): LiveData<Task?> {
        return repository.getTaskById(taskId)
    }

    fun addTask(title: String, description: String) {
        val cleanTitle = title.trim()

        if (cleanTitle.isBlank()) return

        viewModelScope.launch {
            repository.insertTask(
                Task(
                    title = cleanTitle,
                    description = description.trim()
                )
            )
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }
}