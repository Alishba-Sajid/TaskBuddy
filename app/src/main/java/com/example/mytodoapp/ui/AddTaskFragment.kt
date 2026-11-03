
package com.example.mytodoapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mytodoapp.R
import com.example.mytodoapp.domain.model.Task
import com.example.mytodoapp.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : Fragment(R.layout.fragment_add_task) {

    private val taskViewModel: TaskViewModel by activityViewModels()

    private var existingTask: Task? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<TextView>(R.id.tvBackButton)
        val titleInput = view.findViewById<EditText>(R.id.etTaskTitle)
        val descriptionInput = view.findViewById<EditText>(R.id.etTaskDescription)
        val saveButton = view.findViewById<Button>(R.id.btnSaveTask)


        val taskId = arguments?.getLong("taskId", -1L) ?: -1L

        if (taskId != -1L) {
            saveButton.isEnabled = false

            taskViewModel.getTaskById(taskId).observe(viewLifecycleOwner) { task ->
                if (task != null) {
                    existingTask = task

             
                    titleInput.setText(task.title)
                    descriptionInput.setText(task.description)

                    saveButton.isEnabled = true
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Task could not be found",
                        Toast.LENGTH_SHORT
                    ).show()

                    findNavController().navigateUp()
                }
            }
        }

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        saveButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()

            if (title.isBlank()) {
                titleInput.error = getString(R.string.task_title_required)
                titleInput.requestFocus()
                return@setOnClickListener
            }

            val taskToEdit = existingTask

            if (taskToEdit == null) {

                taskViewModel.addTask(title, description)
            } else {

                val updatedTask = taskToEdit.copy(
                    title = title,
                    description = description
                )

                taskViewModel.updateTask(updatedTask)
            }

            findNavController().navigateUp()
        }
    }
}
