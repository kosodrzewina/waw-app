package com.example.wawapp.screens.map

import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wawapp.events.Event
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
    sheetState: BottomSheetState,
    selectedEvent: MutableState<Event?>,
    updateLikeCount: suspend () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ClusteredGoogleMapViewModel = viewModel()
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.warsawLocation, 11f)
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    if (viewModel.isDialogOpen) {
        EventsDialog(
            viewModel.selectedClusterEvents.value,
            selectedEvent = selectedEvent,
            onDismissRequest = viewModel::closeDialog
        ) {
            coroutineScope.launch {
                updateLikeCount()
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
                    coroutineScope.launch {
                        sheetState.expand()
                        updateLikeCount()
                    }
                },
                onClusterClickListener = {
                    viewModel.selectedClusterEvents.value = it.items.toMutableList()
                    viewModel.showDialog()
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
