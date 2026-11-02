
package com.example.mytodoapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AddTaskFragment : Fragment(R.layout.fragment_add_task) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Handle the Back button
        val backButton = view.findViewById<TextView>(R.id.tvBackButton)

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
