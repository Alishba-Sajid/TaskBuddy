
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

    private val addTaskViewModel: AddTaskViewModel by viewModels()

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

            addTaskViewModel.getTaskById(taskId)
                .observe(viewLifecycleOwner) { task ->

                    if (task != null) {
                        existingTask = task

                        binding.etTaskTitle.setText(task.title)
                        binding.etTaskDescription.setText(task.description)

                        binding.btnSaveTask.isEnabled = true
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

        binding.tvBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSaveTask.setOnClickListener {
            val title = binding.etTaskTitle.text.toString().trim()
            val description = binding.etTaskDescription.text.toString().trim()

            if (title.isBlank()) {
                binding.etTaskTitle.error =
                    getString(R.string.task_title_required)

                binding.etTaskTitle.requestFocus()
                return@setOnClickListener
            }

            val taskToEdit = existingTask

            if (taskToEdit == null) {
                addTaskViewModel.addTask(title, description)
            } else {
                val updatedTask = taskToEdit.copy(
                    title = title,
                    description = description
                )

                addTaskViewModel.updateTask(updatedTask)
            }

            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
