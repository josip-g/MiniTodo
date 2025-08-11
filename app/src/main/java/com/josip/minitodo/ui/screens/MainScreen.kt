package com.josip.minitodo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.josip.minitodo.R
import com.josip.minitodo.data.model.Task
import com.josip.minitodo.ui.components.CustomFAB
import com.josip.minitodo.ui.components.MainHeader
import com.josip.minitodo.ui.components.TaskCard
import com.josip.minitodo.ui.components.CustomLoadingSpinner
import com.josip.minitodo.ui.dialogs.DeleteDialog
import com.josip.minitodo.ui.dialogs.LanguageDialog
import com.josip.minitodo.viewmodel.task.TaskViewModel

@Composable
fun MainScreen(
    viewModel: TaskViewModel,
    onEditTask: (Int) -> Unit,
    onAddTask: () -> Unit,
    onGetNotes: () -> Unit,
    currentLang: String,
    onLanguageChange: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<Task?>(null) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        MainHeader(onLanguageClick = { showLanguageDialog = true })

        when {

            uiState.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomLoadingSpinner(
                        modifier = Modifier.size(64.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            uiState.tasks.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = (stringResource(R.string.no_tasks_message)),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 10.dp)
                ) {
                    items(uiState.tasks) { task ->
                        TaskCard(
                            task = task,
                            onToggleDone = { viewModel.toggleTaskDone(task) },
                            onEdit = { onEditTask(task.id) },
                            onDelete = {
                                taskToDelete = task
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CustomFAB(
            onClick = onGetNotes,
            icon = Icons.Default.LibraryBooks,
            contentDescription = "Get notes",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 64.dp, start = 24.dp)
                .navigationBarsPadding()
        )

        CustomFAB(
            onClick = onAddTask,
            icon = Icons.Default.AddTask,
            contentDescription = "Add task",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 64.dp, end = 24.dp)
                .navigationBarsPadding()
        )
    }

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

    if (showDeleteDialog && taskToDelete != null) {
        DeleteDialog(
            title = stringResource(R.string.delete_task_title),
            message = stringResource(R.string.delete_task_message, taskToDelete!!.text),
            confirmButtonText = stringResource(R.string.button_delete),
            dismissButtonText = stringResource(R.string.button_cancel),
            onConfirm = {
                viewModel.deleteTask(taskToDelete!!)
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