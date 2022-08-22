package com.example.wawapp

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.wawapp.dtos.AuthResponseDto
import com.example.wawapp.dtos.LogInDto
import com.example.wawapp.dtos.RegisterDto
import com.example.wawapp.events.EventFetcher
import com.example.wawapp.events.EventStore
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.gson.Gson
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.time.OffsetDateTime

private const val AUTH_DATA_STORE = "auth"
private val Context.authDataStore by preferencesDataStore(
    name = AUTH_DATA_STORE
)

private object PreferenceKey {
    val email = stringPreferencesKey("email")
    val token = stringPreferencesKey("token")
    val expirationDate = stringPreferencesKey("expirationDate")
}

object Auth {
    private const val BASE_URL = "http://10.0.2.2:5000/api/users"
    private const val REGISTER_URL = "$BASE_URL/register"
    private const val LOG_IN_URL = "$BASE_URL/log-in"
    private val gson = Gson()

    var email: String? = null
        private set
    var token: String? by mutableStateOf(null)
        private set
    var expirationDate: OffsetDateTime? = null
        private set

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

    suspend fun logIn(context: Context, logInDto: LogInDto): Boolean {
        val logInDtoSerialized = gson.toJson(logInDto).toString()
        var isSuccess = false

        coroutineScope {
            val result = Fuel.post(LOG_IN_URL)
                .jsonBody(logInDtoSerialized)
                .awaitStringResponseResult()
            val response = result.third.component1()
            val authResponseDto = gson.fromJson(response, AuthResponseDto::class.java)

            authResponseDto?.let { it ->
                isSuccess = it.isSuccess

                if (isSuccess) {
                    email = logInDto.email
                    token = it.message
                    expirationDate = OffsetDateTime.parse(it.expireDate)

                    saveToDataStore(context, PreferenceKey.email, logInDto.email)
                    saveToDataStore(context, PreferenceKey.token, it.message)
                    it.expireDate?.let { expireDate ->
                        saveToDataStore(context, PreferenceKey.expirationDate, expireDate)
                    }
                } else {
                    email = null
                    token = null
                    expirationDate = null

                    saveToDataStore(context, PreferenceKey.email, "")
                    saveToDataStore(context, PreferenceKey.token, "")
                    saveToDataStore(context, PreferenceKey.expirationDate, "")
                }
            }
        }

        return isSuccess
    }

    suspend fun logOut(context: Context) {
        email = null
        token = null
        expirationDate = null

        context.authDataStore.edit {
            it.clear()
        }
        EventStore.favouriteEvents.clear()
    }

    suspend fun checkIfUserIsLoggedIn(context: Context) {
        val data = readFromDataStore(context).first()

        email = data.first
        token = data.second
        data.third?.let {
            expirationDate = OffsetDateTime.parse(it)
        }

        token?.let {
            EventFetcher.fetchFavourites(it)
        }
    }

    private suspend fun saveToDataStore(
        context: Context,
        key: Preferences.Key<String>,
        value: String
    ) {
        context.authDataStore.edit {
            it[key] = value
        }
    }

    private fun readFromDataStore(context: Context): Flow<Triple<String?, String?, String?>> =
        context.authDataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                }
            }.map {
                return@map Triple(
                    it[PreferenceKey.email],
                    it[PreferenceKey.token],
                    it[PreferenceKey.expirationDate]
                )
            }

}
