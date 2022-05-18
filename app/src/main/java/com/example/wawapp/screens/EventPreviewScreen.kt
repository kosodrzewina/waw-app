package com.example.wawapp.screens

import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.example.wawapp.Event

@Composable
fun EventPreviewScreen(event: Event) {
    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Text(text = event.title)
            AndroidView(
                factory = { context -> TextView(context) },
                update = { textView ->
                    textView.text =
                        HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
                }
            )
        }
    }
}