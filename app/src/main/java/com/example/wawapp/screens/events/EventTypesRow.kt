package com.example.wawapp.screens.events

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wawapp.events.EventType
import com.example.wawapp.events.darkerColor
import com.example.wawapp.events.getName

@Composable
fun EventTypesRow(
    selectedTypes: List<EventType>,
    elevation: Dp,
    onItemClick: (EventType) -> Unit
) {
    Surface(
        elevation = elevation,
        color = MaterialTheme.colors.background
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            item { Spacer(modifier = Modifier) }
            items(items = EventType.values()) { eventType ->
                OutlinedButton(
                    shape = CircleShape,
                    border = BorderStroke(1.dp, eventType.color),
                    colors = if (selectedTypes.contains(eventType))
                        ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White, backgroundColor = eventType.color
                        )
                    else
                        ButtonDefaults.outlinedButtonColors(
                            contentColor = eventType.darkerColor,
                            backgroundColor = MaterialTheme.colors.background
                        ),
                    onClick = {
                        onItemClick(eventType)
                    }
                ) {
                    Text(text = eventType.getName(LocalContext.current))
                }
            }
            item { Spacer(modifier = Modifier) }
        }
    }
}
