package com.example.mytodoapp

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(
        view: android.view.View,
        savedInstanceState: android.os.Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Wait for 1.5 seconds, then navigate to Home
        viewLifecycleOwner.lifecycleScope.launch {
            delay(1500.milliseconds)

            findNavController().navigate(
                R.id.action_splashFragment_to_homeFragment
            )
        }
    }
}
