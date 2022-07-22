package com.example.wawapp.screens.map

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wawapp.event.Event
import com.example.wawapp.event.EventStore
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.MapProperties

const val WARSAW_LAT = 52.2297
const val WARSAW_LONG = 21.0122

class ClusteredGoogleMapViewModel : ViewModel() {
    val warsawLocation = LatLng(WARSAW_LAT, WARSAW_LONG)
    val clusterManager = mutableStateOf<ClusterManager<Event>?>(null)
    val properties: MapProperties = MapProperties()
    val events: List<Event> = EventStore.events
}
