
package com.example.mytodoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.data.preferences.UserPreferences
import com.example.mytodoapp.domain.model.Task
import com.example.mytodoapp.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    // Observe all tasks from Room.
    val allTasks: LiveData<List<Task>> = repository.getAllTasks()

    // Retrieve the saved search query from DataStore as LiveData.
    val savedSearchQuery: LiveData<String> =
        userPreferences.getSearchQuery().asLiveData()

    fun getTaskById(taskId: Long): LiveData<Task?> {
        return repository.getTaskById(taskId)
    }

    // Save the user's search query in DataStore.
    fun saveSearchQuery(query: String) {
        viewModelScope.launch {
            userPreferences.saveSearchQuery(query)
        }
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

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}
