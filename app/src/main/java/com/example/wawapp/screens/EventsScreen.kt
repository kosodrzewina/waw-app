package com.example.wawapp.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wawapp.event.Event
import com.example.wawapp.event.EventType
import com.example.wawapp.screens.events.EventList

@Composable
fun EventsScreen(events: List<Event>, context: Context, navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var eventsToDisplay by remember {
        mutableStateOf(events)
    }
    var selectedCategory by remember {
        mutableStateOf("All")
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Events") },
                actions = {
                    if (events.isNotEmpty()) {
                        Text(text = selectedCategory, modifier = Modifier.padding(all = 16.dp))
                        Box {
                            IconButton(
                                onClick = { isExpanded = !isExpanded },
                                modifier = Modifier.padding(
                                    top = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.FilterList,
                                    contentDescription = null
                                )
                            }
                            DropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = { isExpanded = false },
                            ) {
                                DropdownMenuItem(onClick = {
                                    eventsToDisplay = events
                                    isExpanded = false
                                    selectedCategory = "All"
                                }) {
                                    Text(text = "All")
                                }

                                EventType.values().forEach { eventType ->
                                    DropdownMenuItem(onClick = {
                                        eventsToDisplay = events.filter { event ->
                                            event.types.contains(eventType.suffix)
                                        }
                                        isExpanded = false
                                        selectedCategory = eventType.suffix
                                    }) {
                                        Text(text = eventType.suffix)
                                    }
                                }
                            }
                        }
                    }
                }
            )
        }
    ) {
        if (eventsToDisplay.isEmpty() && events.isNotEmpty()) {
            // TODO: Some cool screen
            Text(text = "There's no events in this category")
        }
        EventList(
            events = eventsToDisplay,
            scaffoldState = scaffoldState,
            navController = navController,
            resources = LocalContext.current.resources,
            context = context,
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxHeight()
        )
    }
}
