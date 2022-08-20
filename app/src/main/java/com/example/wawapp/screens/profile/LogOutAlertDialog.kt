package com.example.wawapp.screens.profile

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.wawapp.R

@Composable
fun LogOutAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(id = R.string.logging_out)) },
        text = { Text(text = stringResource(id = R.string.logging_out_ask)) },
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
