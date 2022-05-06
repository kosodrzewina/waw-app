package com.example.wawapp.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wawapp.MapsViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomeScreen(viewModel: MapsViewModel = viewModel()) {
    val scaffoldState = rememberScaffoldState()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(1.35, 103.87), 11f)
    }

    Scaffold(scaffoldState = scaffoldState) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            properties = viewModel.state.properties
        )
    }
}
