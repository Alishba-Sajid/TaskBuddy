package com.example.mytodoapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.data.local.preferences.UserPreferences
import com.example.mytodoapp.domain.model.Task
import com.example.mytodoapp.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    val allTasks: LiveData<List<Task>> = repository.getAllTasks()

    val savedSearchQuery: LiveData<String> =
        userPreferences.getSearchQuery().asLiveData()

    fun saveSearchQuery(query: String) {
        viewModelScope.launch {
            userPreferences.saveSearchQuery(query)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}