package com.example.wawapp.screens.events

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wawapp.EventListViewModelFactory
import com.example.wawapp.R
import com.example.wawapp.events.Event
import com.example.wawapp.events.EventStore
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun EventList(
    events: List<Event>,
    scaffoldState: ScaffoldState,
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
                        event = event,
                        onLikeClick = {
                            coroutineScope.launch {
                                viewModel.likeEvent(context, event.guid)
                            }
                        }
                    ) {
                        viewModel.navigateToEventPreviewScreen(event.guid)
                    }
                }
            }
        }
    }
}
