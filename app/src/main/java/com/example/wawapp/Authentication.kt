package com.example.wawapp

import com.example.wawapp.dtos.AuthResponseDto
import com.example.wawapp.dtos.RegisterDto
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

object Authentication {
    private const val BASE_URL = "http://10.0.2.2:5000/api/users"
    private const val REGISTER_URL = "$BASE_URL/register"
    private val gson = Gson()

    fun register(registerDto: RegisterDto): Boolean {
        val registerDtoSerialized = gson.toJson(registerDto).toString()
        var isSuccess = false

        runBlocking {
            val result = Fuel.post(REGISTER_URL)
                .jsonBody(registerDtoSerialized)
                .awaitStringResponseResult()
            val response = result.third.component1()
            val authResponseDto = gson.fromJson(response, AuthResponseDto::class.java)

            authResponseDto?.isSuccess?.let {
                isSuccess = it
            }
        }

        return isSuccess
    }
}