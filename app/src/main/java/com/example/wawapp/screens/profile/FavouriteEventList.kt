package com.example.wawapp.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wawapp.events.Event
import com.example.wawapp.navigation.Screen
import com.example.wawapp.screens.events.EventListItem
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FavouriteEventList(events: List<Event>, navController: NavController) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = events) { event ->
            EventListItem(
                event = event
            ) {
                navController.navigate(
                    Screen.EventPreviewScreen.routeWithArgs(
                        URLEncoder.encode(event.guid, StandardCharsets.UTF_8.toString()),
                        "true"
                    )
                )
            }
        }
    }
}
