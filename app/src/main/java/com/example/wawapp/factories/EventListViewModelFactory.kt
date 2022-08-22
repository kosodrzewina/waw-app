package com.example.wawapp.factories

import androidx.compose.material.ScaffoldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.wawapp.screens.events.EventListViewModel

class EventListViewModelFactory(
    private val scaffoldState: ScaffoldState,
    private val navController: NavController
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        EventListViewModel(scaffoldState, navController) as T
}
