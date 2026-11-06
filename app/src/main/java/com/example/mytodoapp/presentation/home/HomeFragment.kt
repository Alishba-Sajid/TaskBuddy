
package com.example.mytodoapp.presentation.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodoapp.R
import com.example.mytodoapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        binding.etSearchTasks.text?.clear()

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
                        homeViewModel.deleteTask(task)
                    }
                    .show()
            }
        )

        binding.apply {
            recyclerTasks.layoutManager =
                LinearLayoutManager(requireContext())

            recyclerTasks.adapter = taskAdapter
        }

        homeViewModel.filteredTasks.observe(viewLifecycleOwner) { tasks ->

            taskAdapter.submitList(tasks)

            binding.apply {
                if (tasks.isEmpty()) {
                    recyclerTasks.visibility = View.GONE
                    layoutEmptyState.visibility = View.VISIBLE
                } else {
                    recyclerTasks.visibility = View.VISIBLE
                    layoutEmptyState.visibility = View.GONE
                }
            }
        }

        binding.etSearchTasks.addTextChangedListener { text ->
            homeViewModel.onSearchQueryChanged(
                text?.toString().orEmpty()
            )
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
