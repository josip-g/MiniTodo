package com.josip.minitodo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Language
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.josip.minitodo.R
import com.josip.minitodo.data.Todo
import com.josip.minitodo.viewmodel.TodoViewModel
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures

@Composable
fun MainScreen(
    viewModel: TodoViewModel,
    onEditTodo: (Int) -> Unit,
    onAddTodo: () -> Unit,
    onGetNotes: () -> Unit,
    currentLang: String,
    onLanguageChange: (String) -> Unit
) {
    val todos by viewModel.todos.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<Todo?>(null) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .statusBarsPadding()
                .padding(0.dp)
        ) {
            val logoSize = maxWidth * 0.15f
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .width(logoSize)
                    .aspectRatio(1f)
                    .align(Alignment.CenterStart)
            )

            IconButton(
                onClick = { showLanguageDialog = true },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    Icons.Default.Language,
                    contentDescription = stringResource(R.string.choose_language),
                    tint = Color.White
                )
            }
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = { onEditTodo(todo.id) }
                            )
                        },
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (todo.isDone) Color(0xFFE0E0E0)
                        else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = todo.isDone,
                            onCheckedChange = { viewModel.toggleTodoDone(todo) }
                        )

                        Text(
                            text = (if (todo.isImportant) "❗ " else "") + todo.text,
                            maxLines = 1,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                            style = if (todo.isDone) MaterialTheme.typography.bodyLarge.copy(
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            ) else MaterialTheme.typography.bodyLarge
                        )

                        Row {

                            IconButton(
                                onClick = {
                                    taskToDelete = todo
                                    showDeleteDialog = true
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = stringResource(R.string.delete_note),
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Language dialog
    if (showLanguageDialog) {
        LanguageDialog(
            currentLang = currentLang,
            applyButtonText = stringResource(R.string.button_apply),
            cancelButtonText = stringResource(R.string.button_cancel),
            onLanguageChange = {
                onLanguageChange(it)
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false }
        )
    }

    // Floating buttons
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = onAddTodo,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 24.dp, end = 24.dp)
                .navigationBarsPadding()
        ) {
            Icon(
                Icons.Default.AddTask,
                contentDescription = stringResource(R.string.add_task)
            )
        }

        FloatingActionButton(
            onClick = onGetNotes,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 24.dp, start = 24.dp)
                .navigationBarsPadding()
        ) {
            Icon(
                Icons.Default.LibraryBooks,
                contentDescription = stringResource(R.string.add_note)
            )
        }

        if (showDeleteDialog && taskToDelete != null) {
            DeleteDialog(
                title = stringResource(R.string.delete_task_title),
                message = stringResource(R.string.delete_task_message, taskToDelete!!.text),
                confirmButtonText = stringResource(R.string.button_delete),
                dismissButtonText = stringResource(R.string.button_cancel),
                onConfirm = {
                    viewModel.deleteTodo(taskToDelete!!)
                    showDeleteDialog = false
                    taskToDelete = null
                },
                onDismiss = {
                    showDeleteDialog = false
                    taskToDelete = null
                }
            )
        }
    }
}