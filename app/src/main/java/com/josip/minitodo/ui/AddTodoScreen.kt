package com.josip.minitodo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.josip.minitodo.viewmodel.TodoViewModel

@Composable
fun AddTodoScreen(
    viewModel: TodoViewModel,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TodoForm(
            onSubmit = { newTodo ->
                viewModel.addTodo(
                    newTodo.text,
                    newTodo.isImportant,
                    newTodo.createdAt,
                    newTodo.updatedAt
                )
                onNavigateBack()
            },
            onCancel = onNavigateBack
        )
    }
}
