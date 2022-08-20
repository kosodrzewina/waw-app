package com.example.wawapp.screens.map

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

@Composable
fun PillDragHandler(width: Float) {
    Canvas(modifier = Modifier) {
        drawRoundRect(
            color = Color.LightGray,
            cornerRadius = CornerRadius(10f, 10f),
            topLeft = Offset(-(width / 2), 0f),
            size = Size(width = width, height = 15f)
        )
    }
}
