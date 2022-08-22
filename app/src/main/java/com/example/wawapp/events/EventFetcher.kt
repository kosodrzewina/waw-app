package com.example.wawapp.events

import android.util.Base64
import android.util.Log
import com.example.wawapp.dtos.EventDto
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.net.URL

object EventFetcher {
    private const val BASE_URL = "http://10.0.2.2:5000/api/events"
    private const val URL_BY_TYPE = "$BASE_URL/by-types?eventTypes="
    private const val URL_BY_GUID = "$BASE_URL/by-guid?guid="
    private const val URL_FAVOURITES = "$BASE_URL/favourites"
    private const val URL_LIKE = "$BASE_URL/like"

    suspend fun fetch(vararg types: EventType) {
        withContext(IO) {
            Log.i(javaClass.name, "Fetching events...")

            kotlin.runCatching {
                val response = URL("$URL_BY_TYPE${
                    types.joinToString("&eventTypes=") { it.suffix }
                }").readText()

                val eventListType = object : TypeToken<List<EventDto>>() {}.type
                val fetchedEvents = Gson().fromJson<List<EventDto>>(response, eventListType)
                val events = mapEvents(fetchedEvents)

                EventStore.updateEvents(events)
            }
        }
    }

    fun fetch(guid: String) {
        // TODO: fetch by guid
    }

    suspend fun fetchFavourites(token: String) {
        withContext(IO) {
            Log.i(javaClass.name, "Fetching favourite events...")

            coroutineScope {
                val result = Fuel.get(URL_FAVOURITES)
                    .authentication()
                    .bearer(token)
                    .awaitStringResponseResult()
                val response = result.third.component1()
                val eventListType = object : TypeToken<List<EventDto>>() {}.type
                val eventsDto = Gson().fromJson<List<EventDto>>(response, eventListType)

                eventsDto?.let {
                    val events = mapEvents(eventsDto)
                    EventStore.updateFavouriteEvents(events)
                }
            }
        }
    }

    suspend fun likeEvent(token: String, guid: String, liked: Boolean) {
        val encodedGuid = Base64.encodeToString(guid.toByteArray(), Base64.DEFAULT)

        Log.i(javaClass.name, "Liking event $guid...")
        coroutineScope {
            val (_, response, _) = Fuel
                .put("$URL_LIKE?encodedGuid=$encodedGuid&liked=$liked")
                .authentication()
                .bearer(token)
                .awaitStringResponseResult()

            if (response.statusCode == 200) {
                if (liked) {
                    val event = EventStore.events.first { it.guid == guid }
                    EventStore.favouriteEvents.add(event)
                } else {
                    val event = EventStore.favouriteEvents.first { it.guid == guid }
                    EventStore.favouriteEvents.remove(event)
                }
            }
        }
    }

    private fun mapEvents(eventsDto: List<EventDto>) = eventsDto.map {
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
}
