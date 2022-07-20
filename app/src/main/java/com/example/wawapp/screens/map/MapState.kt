package com.example.wawapp.screens.map

import com.example.wawapp.event.Event
import com.example.wawapp.event.EventStore
import com.google.maps.android.compose.MapProperties

data class MapState(
    val properties: MapProperties = MapProperties(),
    val events: List<Event> = EventStore.events
)
