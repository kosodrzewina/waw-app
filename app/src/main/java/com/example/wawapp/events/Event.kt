package com.example.wawapp.events

import androidx.compose.runtime.MutableState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class Event(
    val eventTitle: String,
    val description: String,
    val url: String,
    val guid: String,
    val isCurrent: Boolean,
    val address: String,
    val imageLink: String,
    val location: LatLng?,
    val likeCount: MutableState<Int>,
    val types: List<EventType>
) : ClusterItem {
    override fun getPosition(): LatLng = location ?: LatLng(0.0, 0.0)

    override fun getTitle(): String = eventTitle

    override fun getSnippet(): String = address

    suspend fun updateLikeCount() {
        likeCount.value = EventManager.getEventLikeCount(guid)
    }
}
