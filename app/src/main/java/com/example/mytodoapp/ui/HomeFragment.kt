package com.example.mytodoapp.ui

import android.os.Bundle
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mytodoapp.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)


        //use view binding instead pf findViewById
        val addTaskButton = view.findViewById<FloatingActionButton>(
            R.id.fabAddTask
        )


        addTaskButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_addTaskFragment
            )
        }
    }
}
