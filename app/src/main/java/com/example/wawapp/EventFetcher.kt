package com.example.wawapp

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.wawapp.event.Event
import com.example.wawapp.event.EventDto
import com.example.wawapp.event.EventStore
import com.example.wawapp.event.EventType
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
            Log.i(javaClass.name, "Fetching events...")
            val response = URL("$URL_BY_TYPE${
                types.joinToString("&eventTypes=") { it.suffix }
            }").readText()
            val eventListType = object : TypeToken<List<EventDto>>() {}.type
            val fetchedEvents = Gson().fromJson<List<EventDto>>(response, eventListType)
            val events = fetchedEvents.map {
                Event(
                    eventTitle = it.title,
                    description = it.description,
                    link = it.link,
                    guid = it.guid,
                    address = it.address,
                    location = mutableStateOf(null),
                    types = it.types
                )
            }

            EventStore.updateEvents(events)
            assignLocationToEvents(EventStore.events, context)
        }
    }

    private fun assignLocationToEvents(events: List<Event>, context: Context) {
        events.forEachIndexed { i, event ->
            Log.i(javaClass.name, "$i/${events.size} Getting location for ${event.address}...")
            event.location.value = getLocationFromAddress(event.address, context)
        }
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
