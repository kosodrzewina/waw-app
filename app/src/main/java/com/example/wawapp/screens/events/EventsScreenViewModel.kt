package com.example.wawapp.screens.events

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wawapp.events.Event
import com.example.wawapp.events.EventType

class EventsScreenViewModel(val events: List<Event>) : ViewModel() {
    var eventsToDisplay = mutableStateOf(events)
    val selectedTypes = mutableStateListOf<EventType>()

    fun clearSelectedTypes() {
        selectedTypes.clear()
        eventsToDisplay.value = events
    }

    fun applyTypeStateChange(eventType: EventType) {
        if (selectedTypes.contains(eventType)) {
            selectedTypes.remove(eventType)
        } else {
            selectedTypes.add(eventType)
        }

        if (selectedTypes.isNotEmpty()) {
            eventsToDisplay.value = events.filter {
                it.types.containsAll(selectedTypes)
            }
        } else {
            eventsToDisplay.value = events
        }
    }
}
