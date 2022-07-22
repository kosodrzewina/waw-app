package com.example.wawapp.screens.map

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wawapp.event.EventStore

@Composable
fun MapScreen(context: Context) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) {
        ClusteredGoogleMap(EventStore.events, context, modifier = Modifier.padding(it))
    }
}
