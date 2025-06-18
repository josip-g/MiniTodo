package com.josip.minitodo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteForm(
    onSave: (String) -> Unit,
    onCancel: () -> Unit
) {
    var noteText by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(text = "Nova bilješka", style = MaterialTheme.typography.titleLarge)

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
            Button(onClick = { onSave(noteText) }, enabled = noteText.isNotBlank()) {
                Text("Spremi")
            }
            OutlinedButton(onClick = onCancel) {
                Text("Odustani")
            }
        }
    }
}
