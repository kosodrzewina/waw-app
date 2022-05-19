package com.example.wawapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wawapp.navigation.BottomNavBar
import com.example.wawapp.navigation.EVENTS_ROUTE
import com.example.wawapp.navigation.EVENT_PREVIEW_ROUTE
import com.example.wawapp.navigation.MAP_ROUTE
import com.example.wawapp.screens.EventPreviewScreen
import com.example.wawapp.screens.EventsScreen
import com.example.wawapp.screens.MapScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    composable(EVENTS_ROUTE) {
                        EventsScreen(
                            events = EventStore.events,
                            navController
                        )
                    }
                    composable(MAP_ROUTE) { MapScreen() }
                    composable(
                        route = "$EVENT_PREVIEW_ROUTE/{encodedGuid}",
                        arguments = listOf(
                            navArgument("encodedGuid") {
                                type = NavType.StringType
                                nullable = false
                            }
                        )
                    ) { entry ->
                        val decodedGuid = URLDecoder.decode(
                            entry.arguments?.getString("encodedGuid"),
                            StandardCharsets.UTF_8.toString()
                        )

                        EventPreviewScreen(
                            event = EventStore.events.first { event ->
                                event.guid == decodedGuid
                            },
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
