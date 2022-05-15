package com.example.wawapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(
    val title: String,
    val icon: ImageVector,
    val navRoute: String
) {
    object EventsScreen : NavItem("Events", Icons.Default.List, EVENTS_ROUTE)
    object MapScreen : NavItem("Map", Icons.Default.Map, MAP_ROUTE)
}
