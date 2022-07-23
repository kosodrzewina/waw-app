package com.example.wawapp.screens.map

import android.content.Context
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
    modifier: Modifier = Modifier,
    viewModel: ClusteredGoogleMapViewModel = viewModel()
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.warsawLocation, 11f)
    }
    val scope = rememberCoroutineScope()

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = viewModel.properties,
        modifier = modifier
    ) {
        MapEffect { googleMap ->
            viewModel.setUpClusters(context, googleMap) {
                scope.launch {
                    sheetState.expand()
                }
            }
        }

        LaunchedEffect(key1 = cameraPositionState.isMoving) {
            if (!cameraPositionState.isMoving) {
                viewModel.clusterManager.value?.onCameraIdle()
            }
        }
    }
}
