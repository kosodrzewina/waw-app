package com.example.wawapp.dtos

data class AuthResponseDto(
    val message: String,
    val isSuccess: Boolean,
    val errors: List<String>?,
    val expireDate: String?
)
