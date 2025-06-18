package com.josip.minitodo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.josip.minitodo.viewmodel.NoteViewModel

@Composable
fun EditNoteScreen(
    noteId: Int,
    viewModel: NoteViewModel,
    onNavigateBack: () -> Unit
) {

    val noteFlow = viewModel.getNoteById(noteId)
    val note by noteFlow.collectAsState(initial = null)
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (note == null) {
        // Show loading or fallback UI while note is loading
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    note?.let {
        NoteForm(
            initialNote = it,
            onSave = { updatedNote ->
                viewModel.updateNote(updatedNote)
                onNavigateBack()
            },
            onCancel = onNavigateBack,
            onDelete = {
                showDeleteDialog = true
            }
        )

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDeleteDialog = false
                },
                title = { Text("Obrisati bilješku?") },
                text = { Text("Jesi li siguran da želiš obrisati ovu bilješku?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deleteNote(it)
                        showDeleteDialog = false
                        onNavigateBack()
                    }) {
                        Text("Obriši")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDeleteDialog = false
                    }) {
                        Text("Odustani")
                    }
                }
            )
        }
    } ?: run {
        Text("Učitavanje...")
    }
}