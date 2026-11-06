package com.example.mytodoapp.presentation.addtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.domain.model.Task
import com.example.mytodoapp.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val taskId: Long = savedStateHandle["taskId"] ?: -1L
    val isEditMode: Boolean = taskId != -1L

    val task: LiveData<Task?> = if (isEditMode) {
        repository.getTaskById(taskId).asLiveData()
    } else {
        MutableLiveData(null)
    }

    fun saveTask(title: String, description: String) {
        val cleanTitle = title.trim()

        if (cleanTitle.isBlank()) return

        val existingTask = task.value

        viewModelScope.launch {
            if (isEditMode) {
                val taskToEdit = existingTask ?: return@launch

                val cleanDescription = description.trim()

                val hasChanges =
                    cleanTitle != taskToEdit.title ||
                            cleanDescription != taskToEdit.description

                if (hasChanges) {
                    repository.updateTask(
                        taskToEdit.copy(
                            title = cleanTitle,
                            description = cleanDescription
                        )
                    )
                }
            } else {
                repository.insertTask(
                    Task(
                        title = cleanTitle,
                        description = description.trim()
                    )
                )
            }
        }
    }
}
