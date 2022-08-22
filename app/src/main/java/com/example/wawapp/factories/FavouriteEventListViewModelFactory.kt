package com.example.wawapp.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.wawapp.screens.profile.FavouriteEventListViewModel

class FavouriteEventListViewModelFactory(private val navController: NavController) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        FavouriteEventListViewModel(navController) as T
}
