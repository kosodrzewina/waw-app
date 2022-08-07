package com.example.wawapp.screens.events

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wawapp.events.Event

@Composable
fun EventsScreen(
    events: List<Event>,
    context: Context,
    navController: NavController,
    viewModel: EventsScreenViewModel = viewModel(factory = EventsScreenViewModelFactory(events))
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Events") },
                actions = {
                    TextButton(onClick = { viewModel.clearSelectedTypes() }) {
                        Text(text = "CLEAR FILTERS", color = Color.White)
                    }
                }
            )
        }
    ) {
        Column {
            EventTypesRow(viewModel.selectedTypes, onItemClick = viewModel::applyTypeStateChange)
            if (viewModel.eventsToDisplay.value.isEmpty() && events.isNotEmpty()) {
                // TODO: Some cool screen
                Text(text = "There's no events in this category")
            }
            EventList(
                events = viewModel.eventsToDisplay.value,
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
}
