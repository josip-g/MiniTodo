package com.josip.minitodo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.josip.minitodo.R
import com.josip.minitodo.data.model.Note
import androidx.compose.ui.res.stringResource
import com.josip.minitodo.common.Constants
import com.josip.minitodo.common.LocaleHelper.formatTimestamp

@Composable
fun NoteForm(
    initialNote: Note? = null,
    onSave: (Note) -> Unit,
    onCancel: () -> Unit,
    onDelete: ((Note) -> Unit)? = null
) {
    var noteText by remember { mutableStateOf(initialNote?.content ?: Constants.EMPTY_STRING) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (initialNote == null)
                    stringResource(id = R.string.new_note)
                else
                    stringResource(id = R.string.edit_note),
                style = MaterialTheme.typography.titleLarge
            )

            if (initialNote != null) {
                IconButton(
                    onClick = { onDelete?.invoke(initialNote) },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete_note),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (initialNote != null) {
            Text(
                text = stringResource(
                    id = R.string.created_with_date,
                    formatTimestamp(initialNote.createdAt)
                ),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
            Text(
                text = stringResource(
                    id = R.string.updated_with_date,
                    formatTimestamp(initialNote.updatedAt)
                ),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = noteText,
            onValueChange = { noteText = it },
            label = { Text(text = stringResource(id = R.string.enter_note)) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            singleLine = false,
            maxLines = Int.MAX_VALUE
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.navigationBarsPadding()
        ) {
            Button(
                onClick = {
                    if (noteText.isNotBlank()) {
                        val now = System.currentTimeMillis()
                        val note = initialNote?.copy(
                            content = noteText,
                            updatedAt = now
                        ) ?: Note(
                            content = noteText,
                            createdAt = now,
                            updatedAt = now
                        )
                        onSave(note)
                    }
                },
                enabled = noteText.isNotBlank()
            ) {
                Text(text = stringResource(id = R.string.button_save))
            }
            OutlinedButton(onClick = onCancel) {
                Text(text = stringResource(id = R.string.button_cancel))
            }
        }
    }
}
