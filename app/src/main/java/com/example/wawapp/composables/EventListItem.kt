package com.example.wawapp.composables

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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wawapp.Event

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventListItem(event: Event, onClick: () -> Unit) {
    var isFavourite by remember {
        mutableStateOf(false)
    }

    Card(shape = RoundedCornerShape(16.dp), elevation = 8.dp, onClick = onClick) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row {
                Text(
                    text = event.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .weight(1f)
                )
                Box(modifier = Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp)) {
                    Icon(
                        imageVector = if (isFavourite) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.clickable {
                            isFavourite = !isFavourite
                        }
                    )
                }
            }
        }
    }
}
