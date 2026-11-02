package com.example.mytodoapp

import android.os.Bundle
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Find the Add Task button
        //use view binding instead pf findViewById
        val addTaskButton = view.findViewById<FloatingActionButton>(
            R.id.fabAddTask
        )

        // Navigate to Add Task screen when clicked
        addTaskButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_addTaskFragment
            )
        }
    }
}
