package com.josip.minitodo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.josip.minitodo.R
import com.josip.minitodo.data.model.Task
import com.josip.minitodo.ui.components.MainHeader
import com.josip.minitodo.ui.components.MainScreenFABs
import com.josip.minitodo.ui.components.TaskCard
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
    val tasks by viewModel.tasks.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<Task?>(null) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        MainHeader(onLanguageClick = { showLanguageDialog = true })
        LazyColumn(
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 10.dp)
        ) {
            items(tasks) { task ->
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

    MainScreenFABs(onAddTask, onGetNotes)

    if (showLanguageDialog) {
        LanguageDialog(
            currentLang = currentLang,
            applyButtonText = stringResource(R.string.button_apply),
            cancelButtonText = stringResource(R.string.button_cancel),
            onLanguageChange = { onLanguageChange(it); showLanguageDialog = false },
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