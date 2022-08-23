package com.example.wawapp.events

import android.util.Base64
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.wawapp.BASE_API_URL
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

object EventManager {
    private const val BASE_EVENTS_URL = "$BASE_API_URL/events"
    private const val BY_TYPE_URL = "$BASE_EVENTS_URL/by-types?eventTypes="
    private const val FAVOURITES_URL = "$BASE_EVENTS_URL/favourites"
    private const val LIKE_URL = "$BASE_EVENTS_URL/like"
    private const val LIKE_COUNT_URL = "$BASE_EVENTS_URL/like-count"

    suspend fun fetchEvents(vararg types: EventType) {
        withContext(IO) {
            Log.i(javaClass.name, "Fetching events...")

            kotlin.runCatching {
                val response = URL("$BY_TYPE_URL${
                    types.joinToString("&eventTypes=") { it.suffix }
                }").readText()

                val eventListType = object : TypeToken<List<EventDto>>() {}.type
                val fetchedEvents = Gson().fromJson<List<EventDto>>(response, eventListType)
                val events = mapEvents(fetchedEvents)

                EventStore.updateEvents(events)
            }
        }
    }

    suspend fun fetchFavourites(token: String) {
        withContext(IO) {
            Log.i(javaClass.name, "Fetching favourite events...")

            coroutineScope {
                val result = Fuel.get(FAVOURITES_URL)
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
                .put("$LIKE_URL?encodedGuid=$encodedGuid&liked=$liked")
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

    suspend fun getEventLikeCount(guid: String): Int {
        val encodedGuid = Base64.encodeToString(guid.toByteArray(), Base64.DEFAULT)

        Log.i(javaClass.name, "Getting like count for event $guid")
        return coroutineScope {
            val (_, response, result) = Fuel
                .get("$LIKE_COUNT_URL?encodedGuid=$encodedGuid")
                .awaitStringResponseResult()

            if (response.statusCode == 200) {
                result.component1()?.toInt()?.let {
                    return@coroutineScope it
                }
            }

            0
        }
    }

    private suspend fun mapEvents(eventsDto: List<EventDto>) = eventsDto.map {
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
            likeCount = mutableStateOf(getEventLikeCount(it.guid)),
            types = it.types.map { type -> stringToEventType(type) }
        )
    }
}
