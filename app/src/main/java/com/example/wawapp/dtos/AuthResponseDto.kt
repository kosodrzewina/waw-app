package com.example.wawapp.dtos

import java.time.OffsetDateTime

data class AuthResponseDto(
    val message: String,
    val isSuccess: Boolean,
    val errors: List<String>?,
    val expireDate: String?
)