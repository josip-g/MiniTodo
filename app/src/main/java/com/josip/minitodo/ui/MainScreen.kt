package com.josip.minitodo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.josip.minitodo.data.Todo
import com.josip.minitodo.viewmodel.TodoViewModel

@Composable
fun MainScreen(
    viewModel: TodoViewModel,
    onEditTodo: (Int) -> Unit,
    onAddTodo: () -> Unit
) {
    val todos by viewModel.todos.collectAsState()
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
        }

        // Floating Add Button at bottom center
        FloatingActionButton(
            onClick = onAddTodo,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add task")
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