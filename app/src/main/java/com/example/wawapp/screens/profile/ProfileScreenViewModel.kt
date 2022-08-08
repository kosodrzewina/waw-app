package com.example.wawapp.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileScreenViewModel : ViewModel() {
    var loginValue by mutableStateOf("")
        private set
    var passwordValue by mutableStateOf("")
        private set
    var passwordVisible by mutableStateOf(false)
        private set

    fun onLoginValueChange(newValue: String) {
        loginValue = newValue
    }

    fun onPasswordValueChange(newValue: String) {
        passwordValue = newValue
    }

    fun switchPasswordVisibility() {
        passwordVisible = !passwordVisible
    }
}