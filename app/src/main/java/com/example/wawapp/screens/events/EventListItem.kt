package com.example.wawapp.screens.events

import android.content.res.Resources
import android.widget.TextView
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.example.wawapp.event.Event
import com.example.wawapp.HtmlImageGetter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventListItem(event: Event, onClick: () -> Unit, resources: Resources) {
    var isFavourite by remember {
        mutableStateOf(false)
    }

    Card(shape = RoundedCornerShape(16.dp), elevation = 8.dp, onClick = onClick) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.eventTitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(all = 16.dp)
                )
                AndroidView(
                    factory = { context -> TextView(context) },
                    update = { textView ->
                        val htmlImageGetter = HtmlImageGetter(resources, textView)

                        textView.text =
                            HtmlCompat.fromHtml(
                                event.description,
                                HtmlCompat.FROM_HTML_MODE_COMPACT,
                                htmlImageGetter,
                                null
                            )
                    },
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
                )
            }
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
