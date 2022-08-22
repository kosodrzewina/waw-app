package com.example.wawapp.screens.profile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.wawapp.navigation.Screen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class FavouriteEventListViewModel(private val navController: NavController) : ViewModel() {
    fun navigateToEventPreviewScreen(guid: String) {
        navController.navigate(
            Screen.EventPreviewScreen.routeWithArgs(
                URLEncoder.encode(guid, StandardCharsets.UTF_8.toString()),
                "true"
            )
        )
    }
}
