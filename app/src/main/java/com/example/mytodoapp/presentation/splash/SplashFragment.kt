package com.example.mytodoapp.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mytodoapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(1500L.milliseconds)
            if (findNavController().currentDestination?.id == R.id.splashFragment) {
                findNavController().navigate(
                    R.id.action_splashFragment_to_homeFragment
                )
            }
        }
    }
}