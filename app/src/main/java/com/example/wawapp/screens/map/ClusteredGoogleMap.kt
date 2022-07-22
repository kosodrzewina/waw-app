package com.example.wawapp.screens.map

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wawapp.event.Event
import com.example.wawapp.event.EventStore
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.*

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun ClusteredGoogleMap(
    events: List<Event>,
    context: Context,
    modifier: Modifier = Modifier,
    viewModel: ClusteredGoogleMapViewModel = viewModel()
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.warsawLocation, 11f)
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = viewModel.properties,
        modifier = modifier
    ) {
        MapEffect(EventStore.events) { googleMap ->
            viewModel.clusterManager.value = ClusterManager<Event>(context, googleMap)
            viewModel.clusterManager.value?.addItems(events)
        }

        LaunchedEffect(key1 = cameraPositionState.isMoving) {
            if (!cameraPositionState.isMoving) {
                viewModel.clusterManager.value?.onCameraIdle()
            }
        }

        MarkerInfoWindow(
            state = rememberMarkerState(position = viewModel.warsawLocation),
        )

//            viewModel.events.forEach { event ->
//                event.location.value?.let { location ->
//                    Marker(
//                        state = MarkerState(location),
//                        title = event.eventTitle,
//                        snippet = event.description
//                    )
//                }
//            }
    }
}