package com.example.wawapp

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import java.util.*

class MapViewModel : ViewModel() {
    var state by mutableStateOf(MapState())
}
