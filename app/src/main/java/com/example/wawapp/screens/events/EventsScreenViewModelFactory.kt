package com.example.wawapp.screens.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wawapp.events.Event

class EventsScreenViewModelFactory(private val events: List<Event>) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        EventsScreenViewModel(events) as T
}