package com.example.wawapp.screens.map

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wawapp.event.Event

private val selectedEvent: MutableState<Event?> = mutableStateOf(null)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(context: Context) {
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetPeekHeight = (-100).dp,
        floatingActionButton = {
            Button(shape = CircleShape, modifier = Modifier.size(45.dp), onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null
                )
            }
        },
        sheetContent = {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp - 200.dp)
            ) {
                selectedEvent.value?.let {
                    Text(
                        text = it.title,
                        fontSize = 36.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    ) {
        ClusteredGoogleMap(
            context = context,
            sheetState = sheetState,
            selectedEvent = selectedEvent
        )
    }
}
