package com.example.wawapp.screens.profile

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.example.wawapp.R

@Composable
fun UnlikeAlertDialog(
    eventTitle: String,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(id = R.string.removing_from_favourites)) },
        text = {
            Text(
                text = buildAnnotatedString {
                    append("${stringResource(id = R.string.removing_from_favourites_ask)}: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(eventTitle)
                    }
                    append("?")
                }
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissClick) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}
