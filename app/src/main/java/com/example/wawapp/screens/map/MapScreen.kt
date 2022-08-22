package com.example.wawapp.screens.map

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wawapp.EventWebView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(viewModel: MapScreenViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val bottomSheetHeight = LocalConfiguration.current.screenHeightDp.dp -
            (LocalConfiguration.current.screenHeightDp.dp / 4)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetPeekHeight = (-100).dp,
        floatingActionButton = {
            Button(
                shape = CircleShape,
                modifier = Modifier.size(45.dp),
                onClick = {
                    coroutineScope.launch {
                        viewModel.likeEvent()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    tint = viewModel.getIconTint(),
                    contentDescription = null
                )
            }
        },
        sheetContent = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                PillDragHandler(width = 150f)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bottomSheetHeight)
            ) {
                viewModel.selectedEvent.value?.let { event ->
                    Text(
                        text = event.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(16.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 8.dp, bottom = 16.dp)
                                .size(15.dp)
                        )
                        Text(
                            text = "100",
                            fontSize = 12.sp,
                            maxLines = 2,
                            fontWeight = FontWeight.Thin,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
                        )
                    }
                    Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        EventWebView(eventUrl = event.url)
                    }
                }
            }
        }
    ) {
        ClusteredGoogleMap(sheetState = sheetState, selectedEvent = viewModel.selectedEvent)
    }
}
