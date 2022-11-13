package com.example.wawapp.screens.events

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.wawapp.Auth
import com.example.wawapp.ErrorHolder
import com.example.wawapp.R
import com.example.wawapp.events.EventHttpClient
import com.example.wawapp.events.EventStore
import com.example.wawapp.events.EventType
import com.example.wawapp.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class EventListViewModel(
    private val scaffoldState: ScaffoldState,
    private val navController: NavController
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    var errorHolder: ErrorHolder by mutableStateOf(ErrorHolder(false, ""))

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    suspend fun likeEvent(context: Context, guid: String) {
        if (Auth.token == null) {
            scaffoldState.snackbarHostState.showSnackbar(
                context.getString(R.string.log_in_to_like_event)
            )
        }

        Auth.token?.let { token ->
            EventHttpClient.likeEvent(
                token,
                guid,
                !EventStore.favouriteEvents.any { it.guid == guid }
            )
        }
    }

    fun navigateToEventPreviewScreen(guid: String) {
        navController.navigate(
            Screen.EventPreviewScreen.routeWithArgs(
                URLEncoder.encode(guid, StandardCharsets.UTF_8.toString()),
                "false"
            )
        )
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)

            withContext(CoroutineScope(IO).coroutineContext) {
                try {
                    EventHttpClient.fetchEvents(*EventType.values())
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    errorHolder.apply {
                        isError = true
                        message = "An error occurred when fetching events!"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    errorHolder.apply {
                        isError = true
                        message = "Unknown error occurred!"
                    }
                }

                _isRefreshing.emit(false)
            }
        }
    }
}
