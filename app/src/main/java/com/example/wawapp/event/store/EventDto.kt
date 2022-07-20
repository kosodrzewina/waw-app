package com.example.wawapp.event.store

data class EventDto(
    val title: String,
    val description: String,
    val link: String,
    val guid: String,
    val address: String,
    val type: String
)