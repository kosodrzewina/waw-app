package com.example.wawapp.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.wawapp.Authentication
import com.example.wawapp.dtos.AuthResponseDto
import com.example.wawapp.dtos.RegisterDto

class ProfileScreenViewModel : ViewModel() {
    var loginValue by mutableStateOf("test@test.test")
        private set
    var passwordValue by mutableStateOf("test123TEST$")
        private set
    var retypedPasswordValue by mutableStateOf("test123TEST$")
        private set
    var isPasswordVisible by mutableStateOf(false)
        private set
    var isRetypedPasswordVisible by mutableStateOf(false)
        private set
    var isRegistration by mutableStateOf(false)
        private set
    var isContactingServer by mutableStateOf(false)
        private set

    fun register(): Boolean {
        isContactingServer = true

        val response = Authentication.register(
            RegisterDto(
                email = loginValue,
                password = passwordValue,
                retypedPassword = retypedPasswordValue
            )
        )

        isContactingServer = false

        return response
    }

    fun onLoginValueChange(newValue: String) {
        loginValue = newValue
    }

    fun onPasswordValueChange(newValue: String) {
        passwordValue = newValue
    }

    fun onRetypedPasswordValueChange(newValue: String) {
        retypedPasswordValue = newValue
    }

    fun switchPasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun switchRetypedPasswordVisibility() {
        isRetypedPasswordVisible = !isRetypedPasswordVisible
    }

    fun setIsRegistration(newValue: Boolean) {
        retypedPasswordValue = ""
        isRegistration = newValue
    }
}