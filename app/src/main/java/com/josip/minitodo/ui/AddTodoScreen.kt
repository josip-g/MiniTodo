package com.josip.minitodo.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.josip.minitodo.viewmodel.TodoViewModel

@Composable
fun AddTodoScreen(
    viewModel: TodoViewModel,
    onNavigateBack: () -> Unit
) {
    TodoForm(
        onSubmit = { newTodo ->
            viewModel.addTodo(newTodo.text, newTodo.isImportant)
            onNavigateBack()
        },
        onCancel = onNavigateBack
    )
}
