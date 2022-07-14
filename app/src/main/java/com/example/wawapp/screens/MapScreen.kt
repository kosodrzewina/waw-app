package com.example.wawapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wawapp.MapsViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

const val WARSAW_LAT = 52.2297
const val WARSAW_LONG = 21.0122

@Composable
fun MapScreen(viewModel: MapsViewModel = viewModel()) {
    val scaffoldState = rememberScaffoldState()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(WARSAW_LAT, WARSAW_LONG), 11f)
    }

    Scaffold(scaffoldState = scaffoldState) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            properties = viewModel.state.properties,
            modifier = Modifier.padding(paddingValues = it)
        )
    }
}
