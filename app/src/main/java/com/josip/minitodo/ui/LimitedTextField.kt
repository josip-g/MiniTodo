package com.josip.minitodo.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LimitedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    minLines: Int = 1,
    maxLines: Int = 3,
    maxChars: Int = 80
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newText ->
            val lines = newText.lines().size
            if (newText.length <= maxChars && lines <= maxLines) {
                onValueChange(newText)
            }
        },
        label = { Text(label) },
        singleLine = false,
        minLines = minLines,
        maxLines = maxLines,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    )
}