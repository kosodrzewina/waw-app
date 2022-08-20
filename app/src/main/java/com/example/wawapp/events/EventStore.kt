package com.example.wawapp.events

import androidx.compose.runtime.mutableStateListOf

object EventStore {
    var events = mutableStateListOf<Event>()
        private set

    fun updateEvents(newEvents: List<Event>) {
        events.clear()
        events.addAll(newEvents)
    }
}
