package com.example.wawapp.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wawapp.R

@Composable
fun Header(email: String) {
    Column {
        Text(
            text = email,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "${stringResource(id = R.string.liked_events)}:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Thin,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )
    }
}
