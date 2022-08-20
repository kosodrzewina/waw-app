package com.example.wawapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.wawapp.EventWebView
import com.example.wawapp.events.Event

@Composable
fun EventPreviewScreen(event: Event, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = event.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        EventWebView(eventUrl = event.url, modifier = Modifier.padding(it))
    }
}
