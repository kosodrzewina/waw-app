package com.example.wawapp.events

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class Event(
    val eventTitle: String,
    val description: String,
    val url: String,
    val guid: String,
    val address: String,
    val location: LatLng?,
    val types: List<EventType>
) : ClusterItem {
    override fun getPosition(): LatLng = location ?: LatLng(0.0, 0.0)

    override fun getTitle(): String = eventTitle

    override fun getSnippet(): String = address
}
