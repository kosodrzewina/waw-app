package com.example.wawapp.screens.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.wawapp.events.Event
import com.example.wawapp.events.EventStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventListItem(
    event: Event,
    onLikeClick: () -> Unit,
    onClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        onClick = onClick,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(event.imageLink),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(64.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.eventTitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(all = 16.dp)
                )
            }
            Column(
                modifier = Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if (EventStore.favouriteEvents.any { it.guid == event.guid })
                        Icons.Default.Favorite
                    else
                        Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.clickable {
                        onLikeClick()
                        coroutineScope.launch {
                            event.updateLikeCount()
                        }
                    }
                )
                Text(
                    text = event.likeCount.value.toString(),
                    fontSize = 12.sp,
                    maxLines = 2,
                    fontWeight = FontWeight.Thin,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
