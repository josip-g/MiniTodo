package com.josip.minitodo.ui

import androidx.compose.foundation.background
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
import com.josip.minitodo.viewmodel.TodoViewModel
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.josip.minitodo.R

@Composable
fun MainScreen(
    viewModel: TodoViewModel,
    onEditTodo: (Int) -> Unit,
    onAddTodo: () -> Unit,
    onGetNotes: () -> Unit,
) {
    val todos by viewModel.todos.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<Todo?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(0.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Mini Todo Logo",
                modifier = Modifier
                    .height(64.dp)
                    .width(64.dp)
                    .statusBarsPadding()
            )
        }

        // Todos List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(todos) { todo ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = (if (todo.isImportant) "❗ " else "") + todo.text,
                            maxLines = 1,
                            modifier = Modifier.weight(1f)
                        )

                        Row {
                            IconButton(
                                onClick = { onEditTodo(todo.id) },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            IconButton(
                                onClick = {
                                    taskToDelete = todo
                                    showDeleteDialog = true
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Floating buttons
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = onAddTodo,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 24.dp, end = 24.dp)
                .navigationBarsPadding() // prevents key overlap
        ) {
            Icon(Icons.Default.AddTask, contentDescription = "Add task")
        }

        FloatingActionButton(
            onClick = onGetNotes,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 24.dp, start = 24.dp)
                .navigationBarsPadding()
        ) {
            Icon(Icons.Default.NoteAlt, contentDescription = "Add note")
        }

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