package com.example.wawapp.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.wawapp.Event
import com.example.wawapp.EventList
import com.example.wawapp.Events

@Composable
fun EventsScreen(events: List<Event>) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Events") })
    }) {
        EventList(events = events)
    }
}
