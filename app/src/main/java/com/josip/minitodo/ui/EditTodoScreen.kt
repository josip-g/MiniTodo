package com.josip.minitodo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.josip.minitodo.data.Todo
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import com.josip.minitodo.viewmodel.TodoViewModel

@Composable
fun EditTodoScreen(
    todoId: Int,
    viewModel: TodoViewModel,
    onNavigateBack: () -> Unit
) {
    val todo by viewModel.getTodoById(todoId).collectAsState(initial = null)

    todo?.let {
        EditTodoForm(
            todo = it,
            onSave = { updatedTodo ->
                viewModel.updateTodo(updatedTodo)
                onNavigateBack()
            },
            onCancel = onNavigateBack
        )
    } ?: run {
        Text("Učitavanje...")
    }
}