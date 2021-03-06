package com.example.wawapp.event

import androidx.compose.runtime.MutableState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class Event(
    val eventTitle: String,
    val description: String,
    val link: String,
    val guid: String,
    val address: String,
    val location: MutableState<LatLng?>,
    val type: String
) : ClusterItem {
    override fun getPosition(): LatLng = location.value ?: LatLng(0.0, 0.0)

    override fun getTitle(): String = eventTitle

    override fun getSnippet(): String = address
}
