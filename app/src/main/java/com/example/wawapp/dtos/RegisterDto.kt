package com.example.wawapp.dtos

data class RegisterDto(
    val email: String,
    val password: String,
    val retypedPassword: String
)