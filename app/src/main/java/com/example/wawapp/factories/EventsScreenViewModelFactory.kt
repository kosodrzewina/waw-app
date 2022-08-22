package com.example.wawapp.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wawapp.events.Event
import com.example.wawapp.screens.events.EventsScreenViewModel

class EventsScreenViewModelFactory(private val events: List<Event>) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        EventsScreenViewModel(events) as T
}
