package com.example.wawapp.navigation

sealed class Screen(val navRoute: String) {
    object EventPreviewScreen : Screen(EVENT_PREVIEW_ROUTE)
}