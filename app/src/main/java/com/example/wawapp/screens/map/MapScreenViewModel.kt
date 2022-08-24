package com.example.wawapp.screens.map

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.wawapp.Auth
import com.example.wawapp.events.Event
import com.example.wawapp.events.EventManager
import com.example.wawapp.events.EventStore

class MapScreenViewModel : ViewModel() {
    val selectedEvent = mutableStateOf<Event?>(null)

    suspend fun likeEvent() {
        selectedEvent.value?.guid?.let { guid ->
            val isLiked = !EventStore.favouriteEvents.any { it.guid == guid }

            Auth.token?.let { token ->
                EventManager.likeEvent(token, guid, isLiked)
            }
        }
    }

    fun getIconTint(): Color {
        selectedEvent.value?.guid?.let { guid ->
            val isLiked = EventStore.favouriteEvents.any { it.guid == guid }

            if (isLiked) {
                return Color.Red
            }
        }

        return Color.Gray
    }
}
