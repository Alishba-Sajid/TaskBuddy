package com.example.mytodoapp.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mytodoapp.R

class AddTaskFragment : Fragment(R.layout.fragment_add_task) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)


        val backButton = view.findViewById<TextView>(R.id.tvBackButton)

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
