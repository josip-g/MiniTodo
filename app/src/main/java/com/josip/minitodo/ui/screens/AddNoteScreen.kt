package com.josip.minitodo.ui.screens

import androidx.compose.runtime.Composable
import com.josip.minitodo.data.model.Note
import com.josip.minitodo.viewmodel.note.NoteViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import com.josip.minitodo.ui.components.NoteForm

@Composable
fun AddNoteScreen(
    viewModel: NoteViewModel,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        NoteForm(
            onSave = { newNote ->
                viewModel.addNote(
                    Note(
                        content = newNote.content,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                )
                onNavigateBack()
            },
            onCancel = onNavigateBack
        )
    }
}