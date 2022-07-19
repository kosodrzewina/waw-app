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

    fun getLocationFromAddress(context: Context, address: String): LatLng? {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: MutableList<Address> = geocoder.getFromLocationName(address, 1)

        if (addresses.isEmpty()) {
            return null
        }

        val location = addresses[0]

        return LatLng(location.latitude, location.longitude)
    }
}
