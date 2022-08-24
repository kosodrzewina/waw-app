package com.example.wawapp.screens.events

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wawapp.EventListViewModelFactory
import com.example.wawapp.R
import com.example.wawapp.events.Event
import com.example.wawapp.events.EventStore
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun EventList(
    events: List<Event>,
    scaffoldState: ScaffoldState,
    lazyListState: LazyListState,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: EventListViewModel = viewModel(
        factory = EventListViewModelFactory(
            scaffoldState,
            navController
        )
    )
) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    if (viewModel.errorHolder.isError) {
        LaunchedEffect(viewModel.errorHolder) {
            scaffoldState.snackbarHostState.showSnackbar(viewModel.errorHolder.message)
            viewModel.errorHolder.isError = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                contentColor = colorResource(
                    id = R.color.accent_color
                )
            )
        },
        onRefresh = { viewModel.refresh() }
    ) {
        if (EventStore.events.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = colorResource(id = R.color.accent_color))
                Text(
                    text = stringResource(id = R.string.loading_events),
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier
            ) {
                items(items = events) { event ->
                    EventListItem(
                        event = event,
                        onLikeClick = {
                            coroutineScope.launch {
                                viewModel.likeEvent(context, event.guid)
                            }
                        },
                        onClick = { viewModel.navigateToEventPreviewScreen(event.guid) }
                    )
                }
            }
        }
    }
}
