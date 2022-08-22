package com.example.wawapp.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wawapp.FavouriteEventListViewModelFactory
import com.example.wawapp.events.Event
import kotlinx.coroutines.launch

@Composable
fun FavouriteEventList(
    events: List<Event>,
    navController: NavController,
    viewModel: FavouriteEventListViewModel = viewModel(
        factory = FavouriteEventListViewModelFactory(
            navController
        )
    )
) {
    val coroutineScope = rememberCoroutineScope()

    if (viewModel.isUnlikeAlertDialog) {
        viewModel.selectedEvent?.eventTitle?.let {
            UnlikeAlertDialog(
                eventTitle = it,
                onDismissRequest = viewModel::closeDialog,
                onConfirmClick = {
                    coroutineScope.launch {
                        viewModel.unlikeEvent()
                        viewModel.closeDialog()
                    }
                },
                onDismissClick = viewModel::closeDialog
            )
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = events) { event ->
            FavouriteEventListItem(
                event = event,
                onUnlikeClick = { viewModel.showDialog(event) }
            ) {
                viewModel.navigateToEventPreviewScreen(event.guid)
            }
        }
    }
}
