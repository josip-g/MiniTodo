package com.josip.minitodo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.josip.minitodo.data.Todo
import com.josip.minitodo.viewmodel.NoteViewModel
import com.josip.minitodo.viewmodel.TodoViewModel

@Composable
fun MainScreen(
    viewModel: TodoViewModel,
    viewModelNotes: NoteViewModel,
    onEditTodo: (Int) -> Unit,
    onAddTodo: () -> Unit,
//    onEditNote: (Int) -> Unit,
    onAddNote: () -> Unit
) {
    val todos by viewModel.todos.collectAsState()
    val notes by viewModelNotes.notes.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<Todo?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Scrollable list of todos
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(todos) { todo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = (if (todo.isImportant) "❗ " else "") + todo.text,
                        modifier = Modifier.weight(1f)
                    )

                    Row {
                        IconButton(onClick = { onEditTodo(todo.id) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(onClick = {
                            taskToDelete = todo
                            showDeleteDialog = true
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Notes", style = MaterialTheme.typography.titleMedium)
            }

            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
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
                    }
                }
            }

        }

        // Floating Add Button at bottom center
        FloatingActionButton(
            onClick = onAddTodo,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 24.dp, end = 24.dp)
        ) {
            Icon(Icons.Default.AddTask, contentDescription = "Add task")
        }

        // Floating Add new Note Button at bottom center
        FloatingActionButton(
            onClick = onAddNote,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 24.dp, start = 24.dp)
        ) {
            Icon(Icons.Default.NoteAlt, contentDescription = "Add note")
        }

        // Delete confirmation dialog
        if (showDeleteDialog && taskToDelete != null) {
            AlertDialog(
                onDismissRequest = {
                    showDeleteDialog = false
                    taskToDelete = null
                },
                title = { Text("Delete task?") },
                text = { Text("Are you sure you want to delete the task \"${taskToDelete!!.text}\"?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deleteTodo(taskToDelete!!)
                        showDeleteDialog = false
                        taskToDelete = null
                    }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDeleteDialog = false
                        taskToDelete = null
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}