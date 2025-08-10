package com.josip.minitodo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoteAdd
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
import com.josip.minitodo.viewmodel.note.NoteViewModel
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.res.stringResource
import com.josip.minitodo.R
import com.josip.minitodo.ui.components.CustomLoadingSpinner
import com.josip.minitodo.ui.components.StaggeredVerticalGrid
import androidx.compose.foundation.layout.size
import com.josip.minitodo.common.Constants.DATE_TIME_FORMAT
import com.josip.minitodo.ui.design.NoteCardCeladon
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NotesScreen(
    viewModelNotes: NoteViewModel,
    onAddNote: () -> Unit,
    onEditNote: (Int) -> Unit,
) {
    val uiState by viewModelNotes.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {

            uiState.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomLoadingSpinner(
                        modifier = Modifier.size(64.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            uiState.notes.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.no_notes_message),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            else -> {
                StaggeredVerticalGrid(
                    maxColumnWidth = 200.dp,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp)
                        .statusBarsPadding()
                ) {
                    uiState.notes.forEach { note ->
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = { onEditNote(note.id) }
                                    )
                                },
                            colors = CardDefaults.cardColors(containerColor = NoteCardCeladon),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = note.content,
                                    maxLines = 10,
                                    style = MaterialTheme.typography.bodyMedium,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                )

                                Text(
                                    text = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
                                        .format(Date(note.updatedAt)),
                                    style = MaterialTheme.typography.bodyMedium,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                                    modifier = Modifier.padding(top = 4.dp)
                                )

                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
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
                .navigationBarsPadding()
        ) {
            Icon(Icons.Default.NoteAdd, contentDescription = stringResource(R.string.add_note))
        }
    }
}