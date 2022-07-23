package com.example.wawapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavBarItem(
    val title: String,
    val icon: ImageVector,
    val navRoute: String
) {
    object EventsScreen : BottomNavBarItem("Events", Icons.Default.List, EVENTS_ROUTE)
    object MapScreen : BottomNavBarItem("Map", Icons.Default.Map, MAP_ROUTE)
    object ProfileScreen : BottomNavBarItem("Profile", Icons.Default.Person, PROFILE_ROUTE)
}
