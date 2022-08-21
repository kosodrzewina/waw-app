package com.example.wawapp.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.wawapp.Auth
import com.example.wawapp.events.EventFetcher
import com.example.wawapp.events.EventStore

class ProfileScreenViewModel : ViewModel() {
    var isLogOutDialog by mutableStateOf(false)
        private set
    var isFetchingFavourites by mutableStateOf(false)
        private set

    fun showDialog() {
        isLogOutDialog = true
    }

    fun closeDialog() {
        isLogOutDialog = false
    }

    suspend fun fetchFavouriteEvents() {
        isFetchingFavourites = true
        Auth.token?.let {
            EventFetcher.fetchFavourites(it)
        }
        isFetchingFavourites = false
    }
}
