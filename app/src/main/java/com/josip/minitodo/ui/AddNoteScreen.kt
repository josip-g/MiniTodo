package com.josip.minitodo.ui

import androidx.compose.runtime.Composable
import com.josip.minitodo.data.Note
import com.josip.minitodo.viewmodel.NoteViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier

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