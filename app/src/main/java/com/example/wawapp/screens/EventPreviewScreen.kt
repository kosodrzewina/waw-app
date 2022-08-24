package com.example.wawapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.wawapp.EventWebView
import com.example.wawapp.R
import com.example.wawapp.events.Event

@Composable
fun EventPreviewScreen(event: Event, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                title = {
                    Text(
                        text = event.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = colorResource(id = R.color.accent_color)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = colorResource(
                                id = R.color.accent_color
                            )
                        )
                    }
                }
            )
        }
    ) {
        EventWebView(eventUrl = event.url, modifier = Modifier.padding(it))
    }
}
