package com.example.mytodoapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodoapp.R
import com.example.mytodoapp.databinding.FragmentHomeBinding
import com.example.mytodoapp.domain.model.Task
import com.example.mytodoapp.ui.adapter.TaskAdapter
import com.example.mytodoapp.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        val allTasks = mutableListOf<Task>()

        var isRestoringSearchQuery = false

        val taskAdapter = TaskAdapter(

            onEditClick = { task ->
                val bundle = Bundle().apply {
                    putLong("taskId", task.id)
                }

                findNavController().navigate(
                    R.id.action_homeFragment_to_addTaskFragment,
                    bundle
                )
            },

            onDeleteClick = { task ->
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.delete_task_title)
                    .setMessage(
                        getString(R.string.delete_task_message, task.title)
                    )
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.delete) { _, _ ->
                        taskViewModel.deleteTask(task)
                    }
                    .show()
            }
        )

        binding.recyclerTasks.layoutManager =
            LinearLayoutManager(requireContext())

        binding.recyclerTasks.adapter = taskAdapter

        fun updateTaskList() {

            val searchText = binding.etSearchTasks.text.toString().trim()

            val filteredTasks = allTasks.filter { task ->
                task.title.contains(searchText, ignoreCase = true) ||
                        task.description.contains(searchText, ignoreCase = true)
            }

            taskAdapter.submitList(filteredTasks)

            if (filteredTasks.isEmpty()) {
                binding.recyclerTasks.visibility = View.GONE
                binding.layoutEmptyState.visibility = View.VISIBLE
            } else {
                binding.recyclerTasks.visibility = View.VISIBLE
                binding.layoutEmptyState.visibility = View.GONE
            }
        }

        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            allTasks.clear()
            allTasks.addAll(tasks)

            updateTaskList()
        }

        binding.etSearchTasks.addTextChangedListener { text ->

            if (!isRestoringSearchQuery) {
                taskViewModel.saveSearchQuery(text.toString())
            }

            updateTaskList()
        }

        taskViewModel.savedSearchQuery.observe(viewLifecycleOwner) { savedQuery ->

            if (binding.etSearchTasks.text.toString() != savedQuery) {

                isRestoringSearchQuery = true

                binding.etSearchTasks.setText(savedQuery)
                binding.etSearchTasks.setSelection(
                    binding.etSearchTasks.text.length
                )

                isRestoringSearchQuery = false
            }

            updateTaskList()
        }

        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_addTaskFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}