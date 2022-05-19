package com.example.wawapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.net.URL

object EventFetcher {
    private const val BASE_URL = "http://10.0.2.2:5001/api/events"
    private const val URL_BY_TYPE = "$BASE_URL/by-types?eventTypes="
    private const val URL_BY_GUID = "$BASE_URL/by-guid?guid="

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun fetch(vararg types: EventType) {
        withContext(IO) {
            val response = URL("$URL_BY_TYPE${
                types.joinToString("&eventTypes=") { it.suffix }
            }").readText()
            val eventListType = object : TypeToken<List<Event>>() {}.type
            val fetchedEvents = Gson().fromJson<List<Event>>(response, eventListType)

            EventStore.updateEvents(fetchedEvents)
        }
    }

    fun fetch(guid: String) {
        // TODO: fetch by guid
    }
}
