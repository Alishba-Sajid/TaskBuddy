package com.example.mytodoapp.presentation.addtask

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mytodoapp.R
import com.example.mytodoapp.databinding.FragmentAddTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : Fragment(R.layout.fragment_add_task) {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private val addEditTaskViewModel: AddEditTaskViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddTaskBinding.bind(view)

        if (addEditTaskViewModel.isEditMode) {
            observeState()
        }

        handleClicks()
    }

    private fun observeState() {
        binding.btnSaveTask.isEnabled = false

        addEditTaskViewModel.task.observe(viewLifecycleOwner) { task ->

            if (task != null) {
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

    private fun handleClicks() {
        binding.apply {

            tvBackButton.setOnClickListener {
                findNavController().navigateUp()
            }

            btnSaveTask.setOnClickListener {

                val title = etTaskTitle.text.toString().trim()
                val description =
                    etTaskDescription.text.toString().trim()

                if (title.isBlank()) {
                    etTaskTitle.error =
                        getString(R.string.task_title_required)

                    etTaskTitle.requestFocus()
                    return@setOnClickListener
                }

                addEditTaskViewModel.saveTask(title, description)

                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
