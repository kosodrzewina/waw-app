package com.example.wawapp.events

import androidx.compose.runtime.mutableStateListOf
import java.text.Collator
import java.util.*

const val LANGUAGE = "pl"

private class EventComparator : Comparator<Event> {
    private val collator = Collator.getInstance(Locale(LANGUAGE))

    override fun compare(p0: Event?, p1: Event?): Int =
        collator.compare(p0?.eventTitle, p1?.eventTitle)
}

object EventStore {
    private val comparator = EventComparator()
    var events = mutableStateListOf<Event>()
        private set
    var favouriteEvents = mutableStateListOf<Event>()
        private set

    fun updateEvents(newEvents: List<Event>) {
        events.clear()
        events.addAll(newEvents)
    }

    fun updateFavouriteEvents(newEvents: List<Event>) {
        favouriteEvents.clear()
        favouriteEvents.addAll(newEvents)
        favouriteEvents.sortWith(comparator)
    }
}
