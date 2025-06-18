package com.josip.minitodo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.josip.minitodo.data.Note

@Composable
fun NoteForm(
    initialNote: Note? = null,
    onSave: (Note) -> Unit,
    onCancel: () -> Unit,
    onDelete: ((Note) -> Unit)? = null
) {
    var noteText by remember { mutableStateOf(initialNote?.content ?: "") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (initialNote == null) "Nova bilješka" else "Uredi bilješku",
                style = MaterialTheme.typography.titleLarge
            )

            // Prikaži Delete samo ako se uređuje postojeca bilješka
            if (initialNote != null) {
                IconButton(
                    onClick = { onDelete?.invoke(initialNote) },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete note",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (initialNote != null) {
            Text(
                text = "Created: ${formatTimestamp(initialNote.createdAt)}",
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = "Updated: ${formatTimestamp(initialNote.updatedAt)}",
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = noteText,
            onValueChange = { noteText = it },
            label = { Text("Unesi bilješku") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            singleLine = false,
            maxLines = Int.MAX_VALUE
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
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
                } }, enabled = noteText.isNotBlank())
            {
                Text("Spremi")
            }
            OutlinedButton(onClick = onCancel) {
                Text("Odustani")
            }
        }
    }
}
