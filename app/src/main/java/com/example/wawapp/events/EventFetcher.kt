package com.example.wawapp.events

import android.util.Log
import com.example.wawapp.dtos.EventDto
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.net.URL

object EventFetcher {
    private const val BASE_URL = "http://10.0.2.2:5000/api/events"
    private const val URL_BY_TYPE = "$BASE_URL/by-types?eventTypes="
    private const val URL_BY_GUID = "$BASE_URL/by-guid?guid="

    suspend fun fetch(vararg types: EventType) {
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
                    url = it.link,
                    guid = it.guid,
                    address = it.address,
                    imageLink = it.image,
                    location = it.location?.let { locationDto ->
                        LatLng(
                            locationDto.latitude,
                            locationDto.longitude
                        )
                    },
                    types = it.types.map { type -> stringToEventType(type) }
                )
            }

            EventStore.updateEvents(events)
        }
    }

    fun fetch(guid: String) {
        // TODO: fetch by guid
    }
}
