package com.example.wawapp.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {
    val navItems = listOf(NavItem.EventsScreen, NavItem.MapScreen)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation {
        navItems.forEach {
            BottomNavigationItem(
                icon = { Icon(imageVector = it.icon, contentDescription = null) },
                label = { Text(text = it.title) },
                selected = currentRoute == it.navRoute,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                onClick = {
                    navController.navigate(it.navRoute)
                }
            )
        }
    }
}