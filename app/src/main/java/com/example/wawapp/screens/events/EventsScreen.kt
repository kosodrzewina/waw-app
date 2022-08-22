package com.example.wawapp.screens.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wawapp.R
import com.example.wawapp.events.Event
import com.example.wawapp.factories.EventsScreenViewModelFactory

@Composable
fun EventsScreen(
    events: List<Event>,
    navController: NavController,
    viewModel: EventsScreenViewModel = viewModel(factory = EventsScreenViewModelFactory(events))
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.events)) },
                actions = {
                    TextButton(onClick = { viewModel.clearSelectedTypes() }) {
                        Text(
                            text = stringResource(id = R.string.clear_filters),
                            color = Color.White
                        )
                    }
                }
            )
        }
    ) {
        Column {
            EventTypesRow(
                selectedTypes = viewModel.selectedTypes,
                onItemClick = viewModel::applyTypeStateChange
            )
            if (viewModel.eventsToDisplay.value.isEmpty() && events.isNotEmpty()) {
                IllustrationView(
                    drawableId = R.drawable.not_found,
                    text = stringResource(id = R.string.no_events)
                )
            }
            EventList(
                events = viewModel.eventsToDisplay.value,
                scaffoldState = scaffoldState,
                navController = navController,
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxHeight()
            )
        }
    }
}
