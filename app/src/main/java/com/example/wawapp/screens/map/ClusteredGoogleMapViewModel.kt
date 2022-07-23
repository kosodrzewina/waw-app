package com.example.wawapp.screens.map

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wawapp.event.Event
import com.example.wawapp.event.EventStore
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.MapProperties

const val WARSAW_LAT = 52.2297
const val WARSAW_LONG = 21.0122

class ClusteredGoogleMapViewModel : ViewModel() {
    private val events: List<Event> = EventStore.events
    val warsawLocation = LatLng(WARSAW_LAT, WARSAW_LONG)
    lateinit var clusterManager: MutableState<ClusterManager<Event>?>
    val properties: MapProperties = MapProperties()

    fun setUpClusters(
        context: Context,
        googleMap: GoogleMap,
        selectedEvent: MutableState<Event?>,
        expandBottomSheet: () -> Unit
    ) {
        clusterManager = mutableStateOf(ClusterManager<Event>(context, googleMap))
        clusterManager.value?.addItems(events)
        clusterManager.value?.setOnClusterItemInfoWindowClickListener {
            selectedEvent.value = it
            expandBottomSheet()
        }
    }
}
