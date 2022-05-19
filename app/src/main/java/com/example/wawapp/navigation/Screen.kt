package com.example.wawapp.navigation

sealed class Screen(val navRoute: String) {
    object EventPreviewScreen : Screen(EVENT_PREVIEW_ROUTE)

    fun routeWithArgs(vararg args: String): String = buildString {
        append(navRoute)
        args.forEach { append("/$it") }
    }
}