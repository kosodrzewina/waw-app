package com.example.wawapp

import androidx.compose.runtime.mutableStateListOf

object Events {
    val events = mutableStateListOf<Event>()

    init {
        events.addAll(
            listOf(
                Event(
                    title = "Test event",
                    descriptionHtml = "Test description",
                    link = "test link",
                    guid = "test guid"
                )
            )
        )
    }
}
