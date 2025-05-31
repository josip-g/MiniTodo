package com.josip.minitodo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.josip.minitodo.data.Todo
import com.josip.minitodo.viewmodel.TodoViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

@Composable
fun MainScreen(viewModel: TodoViewModel) {
    val todos by viewModel.todos.collectAsState()

    var text by remember { mutableStateOf("") }
    var important by remember { mutableStateOf(false) }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<Todo?>(null) }


    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                label = { Text("New Task") }
            )
            Checkbox(
                checked = important,
                onCheckedChange = { important = it }
            )
            Button(onClick = {
                if (text.isNotBlank()) {
                    viewModel.addTodo(text, important)
                    text = ""
                    important = false
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        todos.forEach { todo ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = (if (todo.isImportant) "❗ " else "") + todo.text,
                    modifier = Modifier.weight(1f)
                )
                Row {
                    Button(onClick = {
                        val updated = todo.copy(isImportant = !todo.isImportant)
                        viewModel.updateTodo(updated)
                    }) {
                        Text("Toggle")
                    }
                    Spacer(Modifier.width(8.dp))

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

    if (showDeleteDialog && taskToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                taskToDelete = null
            },
            title = { Text("Obrisati zadatak?") },
            text = { Text("Jesi siguran da želiš obrisati \"${taskToDelete!!.text}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteTodo(taskToDelete!!)
                    showDeleteDialog = false
                    taskToDelete = null
                }) {
                    Text("Obriši")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    taskToDelete = null
                }) {
                    Text("Odustani")
                }
            }
        )
    }
}