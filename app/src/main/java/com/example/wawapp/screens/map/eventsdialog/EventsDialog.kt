package com.example.wawapp.screens.map.eventsdialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wawapp.R
import com.example.wawapp.events.Event

@Composable
fun EventsDialog(
    events: List<Event>,
    selectedEvent: MutableState<Event?>,
    onDismissRequest: () -> Unit,
    expandBottomSheet: () -> Unit
) {
    val maxHeight = LocalConfiguration.current.screenHeightDp / 2

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(id = R.string.events),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        },
        text = {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(items = events) { event ->
                    EventsDialogListItem(
                        event = event,
                        selectedEvent = selectedEvent,
                        onDismissRequest = onDismissRequest,
                        expandBottomSheet = expandBottomSheet
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text(text = stringResource(id = R.string.close))
            }
        },
        containerColor = Color.White,
        modifier = Modifier.heightIn(0.dp, maxHeight.dp)
    )
}
