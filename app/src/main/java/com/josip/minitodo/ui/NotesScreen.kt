package com.josip.minitodo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.josip.minitodo.viewmodel.NoteViewModel
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures

@Composable
fun NotesScreen(
    viewModelNotes: NoteViewModel,
    onAddNote: () -> Unit,
    onEditNote: (Int) -> Unit,
) {
    val notes by viewModelNotes.notes.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Scrollable list of notes
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    onEditNote(note.id)
                                }
                            )
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = note.content)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Created: ${formatTimestamp(note.createdAt)}",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "Updated: ${formatTimestamp(note.updatedAt)}",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "Id: ${note.id.toLong()}",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }

        // Floating Add new Note Button at bottom center
        FloatingActionButton(
            onClick = onAddNote,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add note")
        }
    }
}