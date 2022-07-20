package com.example.wawapp

import com.example.wawapp.event.store.Event
import com.example.wawapp.event.store.EventStore
import com.google.maps.android.compose.MapProperties

data class MapState(
    val properties: MapProperties = MapProperties(),
    val events: List<Event> = EventStore.events
)
