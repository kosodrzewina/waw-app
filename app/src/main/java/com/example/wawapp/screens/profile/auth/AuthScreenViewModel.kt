package com.example.wawapp.screens.profile.auth

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.wawapp.Auth
import com.example.wawapp.dtos.LogInDto
import com.example.wawapp.dtos.RegisterDto
import com.example.wawapp.events.EventFetcher

class AuthScreenViewModel(private val app: Application) : AndroidViewModel(app) {
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

    suspend fun authenticate(): Boolean {
        isContactingServer = true

        val response = if (isRegistration)
            Auth.register(
                RegisterDto(
                    email = loginValue,
                    password = passwordValue,
                    retypedPassword = retypedPasswordValue
                )
            )
        else
            Auth.logIn(
                app,
                LogInDto(
                    email = loginValue,
                    password = passwordValue
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
