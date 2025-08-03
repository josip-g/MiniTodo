package com.josip.minitodo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.josip.minitodo.R
import com.josip.minitodo.data.model.Task
import com.josip.minitodo.common.LocaleHelper.formatTimestamp

@Composable
fun TaskForm(
    initialTask: Task? = null,
    onSubmit: (Task) -> Unit,
    onCancel: () -> Unit
) {
    var text by remember { mutableStateOf(initialTask?.text ?: "") }
    var important by remember { mutableStateOf(initialTask?.isImportant ?: false) }
    val createdAt by remember { mutableStateOf(initialTask?.createdAt ?: System.currentTimeMillis()) }
    val updatedAt by remember { mutableStateOf(initialTask?.updatedAt ?: System.currentTimeMillis()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp)
    ) {
        Text(
            text = if (initialTask == null) stringResource(R.string.new_task) else stringResource(R.string.edit_task),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        LimitedTextField(
            value = text,
            onValueChange = { text = it },
            label = stringResource(R.string.task_label),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = important,
                onCheckedChange = { important = it }
            )
            Text(stringResource(R.string.important_label))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.created_with_date, formatTimestamp(createdAt)),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Text(
            text = stringResource(R.string.updated_with_date, formatTimestamp(updatedAt)),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        val now = System.currentTimeMillis()
                        val task = initialTask?.copy(
                            text = text,
                            isImportant = important,
                            updatedAt = now
                        ) ?: Task(
                            text = text,
                            isImportant = important,
                            createdAt = now,
                            updatedAt = now
                        )
                        onSubmit(task)
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = text.isNotBlank()
            ) {
                Text(stringResource(R.string.button_save))
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.button_cancel))
            }
        }
    }
}