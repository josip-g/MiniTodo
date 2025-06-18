package com.josip.minitodo.ui

import androidx.compose.runtime.Composable
import com.josip.minitodo.data.Note
import com.josip.minitodo.viewmodel.NoteViewModel

@Composable
fun AddNoteScreen(
    viewModel: NoteViewModel,
    onNavigateBack: () -> Unit
) {
    NoteForm(
        onSave = { newNote ->
            viewModel.addNote(
                Note(
                    content = newNote,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
            )
            onNavigateBack()
        },
        onCancel = onNavigateBack
    )
}