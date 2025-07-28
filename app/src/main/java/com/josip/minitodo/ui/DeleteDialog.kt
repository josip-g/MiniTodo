package com.josip.minitodo.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    title: String,
    message: String,
    confirmButtonText: String,
    dismissButtonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            OutlinedButton(onClick = onConfirm) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(dismissButtonText)
            }
        }
    )
}