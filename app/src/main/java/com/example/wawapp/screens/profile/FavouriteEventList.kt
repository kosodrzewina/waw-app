package com.example.wawapp.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wawapp.FavouriteEventListViewModelFactory
import com.example.wawapp.events.Event

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
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = events) { event ->
            FavouriteEventListItem(event, {}) {
                viewModel.navigateToEventPreviewScreen(event.guid)
            }
        }
    }
}
