package com.josip.minitodo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    if (note == null) {
        // Show loading or fallback UI while note is loading
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    note?.let {
        NoteForm(
            initialNote = note!!,
            onSave = { updatedNote ->
                viewModel.updateNote(updatedNote)
                onNavigateBack()
            },
            onCancel = onNavigateBack
        )
    } ?: run {
        Text("Učitavanje...")
    }
}