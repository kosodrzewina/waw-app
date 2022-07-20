package com.example.wawapp

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.example.wawapp.event.store.Event
import com.example.wawapp.event.store.EventDto
import com.example.wawapp.event.store.EventStore
import com.example.wawapp.event.store.EventType
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.*

object EventFetcher {
    private const val BASE_URL = "http://10.0.2.2:5000/api/events"
    private const val URL_BY_TYPE = "$BASE_URL/by-types?eventTypes="
    private const val URL_BY_GUID = "$BASE_URL/by-guid?guid="

    suspend fun fetch(context: Context, vararg types: EventType) {
        withContext(IO) {
            val response = URL("$URL_BY_TYPE${
                types.joinToString("&eventTypes=") { it.suffix }
            }").readText()
            val eventListType = object : TypeToken<List<EventDto>>() {}.type
            val fetchedEvents = Gson().fromJson<List<EventDto>>(response, eventListType)


            EventStore.updateEvents(getEventsWithLocation(fetchedEvents, context))
        }
    }

    private fun getEventsWithLocation(eventsDto: List<EventDto>, context: Context): List<Event> {
        val events = mutableListOf<Event>()

        eventsDto.forEach {
            val location = getLocationFromAddress(it.address, context)

            events.add(
                Event(
                    title = it.title,
                    description = it.description,
                    link = it.link,
                    guid = it.guid,
                    address = it.address,
                    location = location,
                    type = it.type
                )
            )
        }

        return events
    }

    private fun getLocationFromAddress(address: String, context: Context): LatLng? {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: MutableList<Address> = geocoder.getFromLocationName(address, 1)

        if (addresses.isEmpty()) {
            return null
        }

        val location = addresses[0]

        return LatLng(location.latitude, location.longitude)
    }

    fun fetch(guid: String) {
        // TODO: fetch by guid
    }
}
