package com.example.wawapp.screens.map.eventsdialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wawapp.events.Event

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventsDialogListItem(
    event: Event,
    selectedEvent: MutableState<Event?>,
    onDismissRequest: () -> Unit,
    expandBottomSheet: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        onClick = {
            selectedEvent.value = event
            onDismissRequest()
            expandBottomSheet()
        }
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Circle,
                contentDescription = null,
                Modifier.padding(8.dp)
            )
            Text(text = event.title, Modifier.padding(top = 8.dp, end = 8.dp, bottom = 8.dp))
        }
    }
}
