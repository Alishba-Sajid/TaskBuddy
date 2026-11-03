
package com.example.mytodoapp.ui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodoapp.R
import com.example.mytodoapp.ui.adapter.TaskAdapter
import com.example.mytodoapp.ui.viewmodel.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)


        val addTaskButton = view.findViewById<FloatingActionButton>(
            R.id.fabAddTask
        )

        val recyclerView = view.findViewById<RecyclerView>(
            R.id.recyclerTasks
        )

        val emptyState = view.findViewById<LinearLayout>(
            R.id.layoutEmptyState
        )


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
                    .setTitle("Delete task?")
                    .setMessage("Are you sure you want to delete '${task.title}'?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Delete") { _, _ ->
                        taskViewModel.deleteTask(task)
                    }
                    .show()
            }
        )


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = taskAdapter


        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->

            taskAdapter.submitList(tasks)

            if (tasks.isEmpty()) {
                recyclerView.visibility = View.GONE
                emptyState.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                emptyState.visibility = View.GONE
            }
        }


        addTaskButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_addTaskFragment
            )
        }
    }
}
