package com.example.mytodoapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.domain.model.Task
import com.example.mytodoapp.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    private val allTasks: LiveData<List<Task>> =
        repository.getAllTasks().asLiveData()
    private val searchQuery = MutableLiveData("")

    val filteredTasks: LiveData<List<Task>> =
        MediatorLiveData<List<Task>>().apply {

            var currentTasks: List<Task> = emptyList()
            var currentQuery = ""

            addSource(allTasks) { tasks ->
                currentTasks = tasks
                value = filterTasks(currentTasks, currentQuery)
            }

            addSource(searchQuery) { query ->
                currentQuery = query
                value = filterTasks(currentTasks, currentQuery)
            }
        }
    private fun filterTasks(
        tasks: List<Task>,
        query: String
    ): List<Task> {
        return tasks.filter { task ->
            task.title.contains(query, ignoreCase = true) ||
                    task.description.contains(query, ignoreCase = true)
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query.trim()
    }
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}
