package com.example.wawapp.screens.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wawapp.EventsScreenViewModelFactory
import com.example.wawapp.R
import com.example.wawapp.events.Event

@Composable
fun EventsScreen(
    events: List<Event>,
    navController: NavController,
    viewModel: EventsScreenViewModel = viewModel(factory = EventsScreenViewModelFactory(events))
) {
    val scaffoldState = rememberScaffoldState()
    val lazyListState = rememberLazyListState()
    val firstItemVisible by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colors.background,
                title = {
                    Text(
                        text = stringResource(id = R.string.events),
                        color = colorResource(id = R.color.accent_color),
                        fontSize = 24.sp
                    )
                },
                actions = {
                    TextButton(onClick = { viewModel.clearSelectedTypes() }) {
                        Text(
                            text = stringResource(id = R.string.clear_filters),
                            color = colorResource(id = R.color.accent_color)
                        )
                    }
                }
            )
        }
    ) {
        Column {
            EventTypesRow(
                elevation = if (firstItemVisible) 0.dp else 8.dp,
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
                lazyListState = lazyListState,
                navController = navController,
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxHeight()
            )
        }
    }
}
