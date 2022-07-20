package com.example.wawapp.screens.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.wawapp.screens.map.MapState

class MapViewModel : ViewModel() {
    var state by mutableStateOf(MapState())
}
