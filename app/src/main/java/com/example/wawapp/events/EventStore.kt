package com.example.wawapp.events

import androidx.compose.runtime.mutableStateListOf

object EventStore {
    private val EVENTS = mutableStateListOf<Event>()
    val events: List<Event> get() = EVENTS

    fun updateEvents(newEvents: List<Event>) {
        EVENTS.addAll(newEvents.filter { fetchedEvent ->
            EVENTS.all { event ->
                event.guid != fetchedEvent.guid
            }
        })
    }
}
