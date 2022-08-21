package com.example.wawapp.screens.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wawapp.R
import com.example.wawapp.events.Event
import com.example.wawapp.events.EventStore
import com.example.wawapp.navigation.Screen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun EventList(
    events: List<Event>,
    scaffoldState: ScaffoldState,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: EventListViewModel = viewModel()
) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    if (viewModel.errorHolder.isError) {
        LaunchedEffect(viewModel.errorHolder) {
            scaffoldState.snackbarHostState.showSnackbar(viewModel.errorHolder.message)
            viewModel.errorHolder.isError = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.refresh() }
    ) {
        if (EventStore.events.isEmpty()) {
            IllustrationView(
                drawableId = R.drawable.empty_street,
                text = stringResource(id = R.string.empty),
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier
            ) {
                items(items = events) { event ->
                    EventListItem(
                        event = event
                    ) {
                        navController.navigate(
                            Screen.EventPreviewScreen.routeWithArgs(
                                URLEncoder.encode(event.guid, StandardCharsets.UTF_8.toString()),
                                "false"
                            )
                        )
                    }
                }
            }
        }
    }
}
