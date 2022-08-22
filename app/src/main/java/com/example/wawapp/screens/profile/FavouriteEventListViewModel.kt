package com.example.wawapp.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.wawapp.Auth
import com.example.wawapp.events.Event
import com.example.wawapp.events.EventManager
import com.example.wawapp.navigation.Screen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class FavouriteEventListViewModel(private val navController: NavController) : ViewModel() {
    var isUnlikeAlertDialog by mutableStateOf(false)
        private set
    var selectedEvent by mutableStateOf<Event?>(null)
        private set

    fun showDialog(selectedEvent: Event) {
        this.selectedEvent = selectedEvent
        isUnlikeAlertDialog = true
    }

    fun closeDialog() {
        selectedEvent = null
        isUnlikeAlertDialog = false
    }

    suspend fun unlikeEvent() {
        Auth.token?.let { token ->
            selectedEvent?.guid?.let { guid ->
                EventManager.likeEvent(token, guid, false)
            }
        }
    }

    fun navigateToEventPreviewScreen(guid: String) {
        navController.navigate(
            Screen.EventPreviewScreen.routeWithArgs(
                URLEncoder.encode(guid, StandardCharsets.UTF_8.toString()),
                "true"
            )
        )
    }
}
