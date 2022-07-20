package com.example.wawapp.screens.events

import android.content.Context
import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wawapp.composables.EmptyView
import com.example.wawapp.composables.EventListItem
import com.example.wawapp.event.Event
import com.example.wawapp.event.EventStore
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
    resources: Resources,
    context: Context,
    modifier: Modifier = Modifier
) {
    val viewModel: EventListViewModel = viewModel()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val scope = rememberCoroutineScope()

    if (viewModel.errorHolder.isError) {
        LaunchedEffect(viewModel.errorHolder) {
            scaffoldState.snackbarHostState.showSnackbar(viewModel.errorHolder.message)
            viewModel.errorHolder.isError = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.refresh(context) }
    ) {
        if (EventStore.events.isEmpty()) {
            EmptyView(
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
                        event = event,
                        resources = resources,
                        onClick = {
                            navController.navigate(
                                Screen.EventPreviewScreen.routeWithArgs(
                                    URLEncoder.encode(event.guid, StandardCharsets.UTF_8.toString())
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}
