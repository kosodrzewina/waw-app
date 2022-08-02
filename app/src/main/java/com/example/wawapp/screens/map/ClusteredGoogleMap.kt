package com.example.wawapp.screens.map

import android.content.Context
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wawapp.event.Event
import com.example.wawapp.screens.map.eventsdialog.EventsDialog
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(MapsComposeExperimentalApi::class, ExperimentalMaterialApi::class)
@Composable
fun ClusteredGoogleMap(
    context: Context,
    sheetState: BottomSheetState,
    selectedEvent: MutableState<Event?>,
    modifier: Modifier = Modifier,
    viewModel: ClusteredGoogleMapViewModel = viewModel()
) {
    var isDialogOpen by remember {
        mutableStateOf(false)
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.warsawLocation, 11f)
    }
    val scope = rememberCoroutineScope()

    if (isDialogOpen) {
        EventsDialog(
            viewModel.selectedClusterEvents.value,
            selectedEvent = selectedEvent,
            onDismissRequest = {
                isDialogOpen = false
            }
        ) {
            scope.launch {
                sheetState.expand()
            }
        }
    }
    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = viewModel.properties,
        modifier = modifier
    ) {
        MapEffect { googleMap ->
            viewModel.setUpClusters(
                context,
                googleMap,
                selectedEvent,
                expandBottomSheet = {
                    scope.launch {
                        sheetState.expand()
                    }
                },
                onClusterClickListener = {
                    viewModel.selectedClusterEvents.value = it.items.toMutableList()
                    isDialogOpen = true
                    true
                }
            )
        }

        LaunchedEffect(key1 = cameraPositionState.isMoving) {
            if (!cameraPositionState.isMoving) {
                viewModel.clusterManager.value?.onCameraIdle()
            }
        }
    }
}
