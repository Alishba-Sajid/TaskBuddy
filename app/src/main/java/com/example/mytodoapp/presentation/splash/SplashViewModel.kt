package com.example.mytodoapp.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds
class SplashViewModel : ViewModel() {
    private val _navigateToHome = MutableLiveData(false)
    val navigateToHome: LiveData<Boolean> = _navigateToHome
    private var hasStarted = false
    fun startSplash() {
        if (hasStarted) return

        hasStarted = true

        viewModelScope.launch {
            delay(1500L.milliseconds)
            _navigateToHome.value = true
        }
    }
    fun onNavigationCompleted() {
        _navigateToHome.value = false
    }
}
