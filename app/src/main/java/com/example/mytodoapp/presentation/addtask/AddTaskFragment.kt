package com.example.mytodoapp.presentation.addtask

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mytodoapp.R
import com.example.mytodoapp.databinding.FragmentAddTaskBinding
import com.example.mytodoapp.domain.model.Task
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class AddTaskFragment : Fragment(R.layout.fragment_add_task) {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private val addEditTaskViewModel: AddEditTaskViewModel by viewModels()
    private var existingTask: Task? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddTaskBinding.bind(view)

        val taskId = arguments?.getLong("taskId", -1L) ?: -1L

        if (taskId != -1L) {
            binding.btnSaveTask.isEnabled = false

            addEditTaskViewModel.getTaskById(taskId)
                .observe(viewLifecycleOwner) { task ->
                    if (task != null) {
                        existingTask = task

                        binding.apply {
                            etTaskTitle.setText(task.title)
                            etTaskDescription.setText(task.description)
                            btnSaveTask.isEnabled = true
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.task_not_found),
                            Toast.LENGTH_SHORT
                        ).show()

                        findNavController().navigateUp()
                    }
                }
        }

        binding.apply {
            tvBackButton.setOnClickListener {
                findNavController().navigateUp()
            }

            btnSaveTask.setOnClickListener {
                val title = etTaskTitle.text.toString().trim()
                val description = etTaskDescription.text.toString().trim()

                if (title.isBlank()) {
                    etTaskTitle.error =
                        getString(R.string.task_title_required)

                    etTaskTitle.requestFocus()
                    return@setOnClickListener
                }

                val taskToEdit = existingTask

                if (taskToEdit == null) {
                    addEditTaskViewModel.addTask(title, description)
                } else {
                    val hasChanges =
                        title != taskToEdit.title ||
                                description != taskToEdit.description

                    if (hasChanges) {
                        val updatedTask = taskToEdit.copy(
                            title = title,
                            description = description
                        )
                        addEditTaskViewModel.updateTask(updatedTask)
                    }
                }
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
