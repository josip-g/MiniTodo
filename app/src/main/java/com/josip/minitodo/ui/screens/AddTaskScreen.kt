package com.josip.minitodo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.josip.minitodo.ui.components.TaskForm
import com.josip.minitodo.viewmodel.task.TaskViewModel

@Composable
fun AddTaskScreen(
    viewModel: TaskViewModel,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TaskForm(
            onSubmit = { newTodo ->
                viewModel.addTask(
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
