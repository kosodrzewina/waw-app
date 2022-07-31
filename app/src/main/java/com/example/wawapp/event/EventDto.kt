package com.example.wawapp.event

data class EventDto(
    val title: String,
    val description: String,
    val link: String,
    val guid: String,
    val address: String,
    val types: List<String>
)