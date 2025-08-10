package com.josip.minitodo.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.josip.minitodo.viewmodel.task.TaskViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.josip.minitodo.ui.components.CustomLoadingSpinner
import com.josip.minitodo.ui.components.TaskForm

@Composable
fun EditTaskScreen(
    taskId: Int,
    viewModel: TaskViewModel,
    onNavigateBack: () -> Unit
) {
    val todo by viewModel.getTaskById(taskId).collectAsState(initial = null)

    if (todo == null) {

        // Progress bar while data is loading
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            CustomLoadingSpinner(
                modifier = Modifier.size(64.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
        return
    }

    todo?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            TaskForm(
                initialTask = it,
                onSubmit = { updatedTodo ->
                    viewModel.updateTask(updatedTodo)
                    onNavigateBack()
                },
                onCancel = onNavigateBack
            )
        }
    } ?: run {
        CustomLoadingSpinner(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}