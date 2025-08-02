package com.josip.minitodo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.josip.minitodo.R
import com.josip.minitodo.common.Constants.TEXT_FIELD_MIN_LINES
import com.josip.minitodo.common.Constants.TEXT_FIELD_MAX_LINES
import com.josip.minitodo.common.Constants.TEXT_FIELD_MAX_CHARS
import com.josip.minitodo.common.Constants.EMPTY_STRING

@Composable
fun LimitedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    minLines: Int = TEXT_FIELD_MIN_LINES,
    maxLines: Int = TEXT_FIELD_MAX_LINES,
    maxChars: Int = TEXT_FIELD_MAX_CHARS
) {
    Column(modifier = modifier) {
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
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(onClick = { onValueChange(EMPTY_STRING) }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.clear_text),
                        )
                    }
                }
            }
        )

        Text(
            text = "${value.length} / $maxChars",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            textAlign = TextAlign.End,
            color = Color.Gray
        )
    }
}