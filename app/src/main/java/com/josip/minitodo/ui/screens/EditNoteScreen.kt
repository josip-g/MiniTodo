package com.josip.minitodo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.josip.minitodo.viewmodel.note.NoteViewModel
import androidx.compose.ui.res.stringResource
import com.josip.minitodo.R
import com.josip.minitodo.ui.dialogs.DeleteDialog
import com.josip.minitodo.ui.components.NoteForm
import com.josip.minitodo.ui.components.CustomLoadingSpinner
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            CustomLoadingSpinner(
                modifier = Modifier.size(64.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
        return
    }

    note?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
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
        }

        if (showDeleteDialog) {
            DeleteDialog(
                title = stringResource(R.string.delete_note_title),
                message = stringResource(R.string.delete_note_message),
                confirmButtonText = stringResource(R.string.button_delete),
                dismissButtonText = stringResource(R.string.button_cancel),
                onConfirm = {
                    viewModel.deleteNote(it)
                    showDeleteDialog = false
                    onNavigateBack()
                },
                onDismiss = { showDeleteDialog = false }
            )
        }
    } ?: run {
        CustomLoadingSpinner(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}