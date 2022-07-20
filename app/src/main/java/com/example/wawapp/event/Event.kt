package com.example.wawapp.event

import com.google.android.gms.maps.model.LatLng

data class Event(
    val title: String,
    val description: String,
    val link: String,
    val guid: String,
    val address: String,
    val location: LatLng?,
    val type: String
)
