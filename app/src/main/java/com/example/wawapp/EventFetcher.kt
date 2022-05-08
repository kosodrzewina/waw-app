package com.example.wawapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

object EventFetcher {
    private const val BASE_URL = "http://10.0.2.2:7208/api/events"
    private const val URL_BY_TYPE = "$BASE_URL/by-types?eventTypes="
    private const val URL_BY_GUID = "$BASE_URL/by-guid?guid="

    @OptIn(DelicateCoroutinesApi::class)
    fun fetch(vararg types: Type) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = URL("$URL_BY_TYPE${
                types.joinToString("&eventTypes=") { it.suffix }
            }").readText()
            val eventListType = object : TypeToken<List<Event>>() {}.type
            val fetchedEvents = Gson().fromJson<List<Event>>(response, eventListType)

            Events.events.addAll(fetchedEvents.filter { fetchedEvent ->
                Events.events.all { event ->
                    event.guid != fetchedEvent.guid
                }
            })
        }
    }

    fun fetch(guid: String) {
        // TODO: fetch by guid
    }
}
