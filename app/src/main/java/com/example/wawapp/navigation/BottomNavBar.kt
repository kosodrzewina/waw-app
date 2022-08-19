package com.example.wawapp.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wawapp.R

@Composable
fun BottomNavBar(navController: NavController) {
    val bottomNavBarItems = listOf(
        BottomNavBarItem(
            title = LocalContext.current.getString(R.string.events),
            icon = Icons.Default.LocalActivity,
            navRoute = EVENTS_ROUTE
        ),
        BottomNavBarItem(
            title = LocalContext.current.getString(R.string.map),
            icon = Icons.Default.Map,
            navRoute = MAP_ROUTE
        ),
        BottomNavBarItem(
            title = LocalContext.current.getString(R.string.profile),
            icon = Icons.Default.AccountCircle,
            navRoute = AUTH_ROUTE
        ),
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation {
        bottomNavBarItems.forEach {
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