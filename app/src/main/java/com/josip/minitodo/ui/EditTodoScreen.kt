package com.josip.minitodo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.josip.minitodo.viewmodel.TodoViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.josip.minitodo.R

@Composable
fun EditTodoScreen(
    todoId: Int,
    viewModel: TodoViewModel,
    onNavigateBack: () -> Unit
) {
    val todo by viewModel.getTodoById(todoId).collectAsState(initial = null)

    if (todo == null) {

        // Progress bar while data is loading
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
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
            TodoForm(
                initialTodo = it,
                onSubmit = { updatedTodo ->
                    viewModel.updateTodo(updatedTodo)
                    onNavigateBack()
                },
                onCancel = onNavigateBack
            )
        }
    } ?: run {
        Text(stringResource(R.string.loading))
    }
}