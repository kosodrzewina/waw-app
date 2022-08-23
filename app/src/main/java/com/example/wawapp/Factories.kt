@file:Suppress("UNCHECKED_CAST")

package com.example.wawapp

import androidx.compose.material.ScaffoldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.wawapp.events.Event
import com.example.wawapp.screens.events.EventListViewModel
import com.example.wawapp.screens.events.EventsScreenViewModel
import com.example.wawapp.screens.profile.FavouriteEventListViewModel

class EventListViewModelFactory(
    private val scaffoldState: ScaffoldState,
    private val navController: NavController
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        EventListViewModel(scaffoldState, navController) as T
}

class EventsScreenViewModelFactory(private val events: List<Event>) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        EventsScreenViewModel(events) as T
}

class FavouriteEventListViewModelFactory(private val navController: NavController) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        FavouriteEventListViewModel(navController) as T
}
