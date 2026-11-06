package com.example.mytodoapp.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mytodoapp.R
class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        splashViewModel.navigateToHome.observe(viewLifecycleOwner) { shouldNavigate ->

            if (
                shouldNavigate &&
                findNavController().currentDestination?.id == R.id.splashFragment
            ) {
                splashViewModel.onNavigationCompleted()

                findNavController().navigate(
                    R.id.action_splashFragment_to_homeFragment
                )
            }
        }

        splashViewModel.startSplash()
    }
}