package com.example.wawapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wawapp.navigation.BottomNavBar
import com.example.wawapp.navigation.EVENTS_ROUTE
import com.example.wawapp.navigation.MAP_ROUTE
import com.example.wawapp.screens.EventsScreen
import com.example.wawapp.screens.MapScreen
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch {
            EventFetcher.fetch(*EventType.values())
        }

        setContent {
            val navController = rememberNavController()

            Scaffold(
                bottomBar = { BottomNavBar(navController = navController) }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = EVENTS_ROUTE,
                    modifier = Modifier.padding(paddingValues = it)
                ) {
                    composable(EVENTS_ROUTE) { EventsScreen(events = Events.events) }
                    composable(MAP_ROUTE) { MapScreen() }
                }
            }
        }
    }
}
