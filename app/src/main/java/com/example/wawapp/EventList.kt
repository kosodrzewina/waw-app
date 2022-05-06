package com.example.wawapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.wawapp.composables.EventListItem

@Composable
fun EventList(events: List<Event>) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = events) {
            EventListItem(
                event = it
                // TODO: onClick -> display event details, onLongClick -> add to favourites
            )
        }
    }
}
