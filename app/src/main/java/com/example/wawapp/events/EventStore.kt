package com.example.wawapp.events

import androidx.compose.runtime.mutableStateListOf

object EventStore {
    var events = mutableStateListOf<Event>()
        private set
    var favouriteEvents = mutableStateListOf<Event>()
        private set

    fun updateEvents(newEvents: List<Event>) {
        events.clear()
        events.addAll(newEvents)
    }

    fun updateFavouriteEvents(newEvents: List<Event>) {
        favouriteEvents.clear()
        favouriteEvents.addAll(newEvents)
    }
}
