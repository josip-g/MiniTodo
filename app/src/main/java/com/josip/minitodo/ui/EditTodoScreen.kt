package com.josip.minitodo.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.josip.minitodo.viewmodel.TodoViewModel

@Composable
fun EditTodoScreen(
    todoId: Int,
    viewModel: TodoViewModel,
    onNavigateBack: () -> Unit
) {
    val todo by viewModel.getTodoById(todoId).collectAsState(initial = null)

    todo?.let {
        TodoForm(
            initialTodo = it,
            onSubmit = { updatedTodo ->
                viewModel.updateTodo(updatedTodo)
                onNavigateBack()
            },
            onCancel = onNavigateBack
        )
    } ?: run {
        Text("Učitavanje...")
    }
}