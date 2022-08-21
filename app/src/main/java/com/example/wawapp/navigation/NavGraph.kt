package com.example.wawapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wawapp.events.EventStore
import com.example.wawapp.screens.EventPreviewScreen
import com.example.wawapp.screens.events.EventsScreen
import com.example.wawapp.screens.map.MapScreen
import com.example.wawapp.screens.profile.AuthScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController, startDestination = EVENTS_ROUTE, modifier = modifier
    ) {
        composable(EVENTS_ROUTE) {
            EventsScreen(events = EventStore.events, navController = navController)
        }

        composable(MAP_ROUTE) { MapScreen() }

        composable(AUTH_ROUTE) { AuthScreen(navController) }

        composable(
            route = "$EVENT_PREVIEW_ROUTE/{encodedGuid}/{isFavourite}",
            arguments = listOf(
                navArgument("encodedGuid") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("isFavourite") {
                    type = NavType.BoolType
                    nullable = false
                }
            )
        ) { entry ->
            val decodedGuid = URLDecoder.decode(
                entry.arguments?.getString("encodedGuid"),
                StandardCharsets.UTF_8.toString()
            )
            val isFavourite = entry.arguments?.getBoolean("isFavourite")

            isFavourite?.let {
                val events = if (it) EventStore.favouriteEvents else EventStore.events

                EventPreviewScreen(
                    event = events.first { event ->
                        event.guid == decodedGuid
                    },
                    navController = navController
                )
            }
        }
    }
}
