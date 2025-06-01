package com.josip.minitodo.ui

import androidx.compose.runtime.Composable
import com.josip.minitodo.viewmodel.TodoViewModel

@Composable
fun AddTodoScreen(
    viewModel: TodoViewModel,
    onNavigateBack: () -> Unit
) {
    TodoForm(
        onSubmit = { newTodo ->
            viewModel.addTodo(newTodo.text, newTodo.isImportant, newTodo.createdAt, newTodo.updatedAt)
            onNavigateBack()
        },
        onCancel = onNavigateBack
    )
}
