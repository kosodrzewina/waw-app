package com.example.wawapp.dtos

data class EventDto(
    val title: String,
    val description: String,
    val link: String,
    val guid: String,
    val address: String,
    val image: String,
    val location: LocationDto?,
    val types: List<String>
)