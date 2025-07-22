package com.josip.minitodo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.josip.minitodo.data.Todo
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TodoForm(
    initialTodo: Todo? = null,
    onSubmit: (Todo) -> Unit,
    onCancel: () -> Unit
) {
    var text by remember { mutableStateOf(initialTodo?.text ?: "") }
    var important by remember { mutableStateOf(initialTodo?.isImportant ?: false) }
    val createdAt by remember { mutableStateOf(initialTodo?.createdAt ?: System.currentTimeMillis()) }
    val updatedAt by remember { mutableStateOf(initialTodo?.updatedAt ?: System.currentTimeMillis()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = if (initialTodo == null) "New todo" else "Edit todo",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Todo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = important,
                onCheckedChange = { important = it }
            )
            Text("Important")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Created At: ${formatTimestamp(createdAt)}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Text(
            text = "Last update: ${formatTimestamp(updatedAt)}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                if (text.isNotBlank()) {
                    val now = System.currentTimeMillis()
                    val todo = initialTodo?.copy(
                        text = text,
                        isImportant = important,
                        updatedAt = now
                    ) ?: Todo(
                        text = text,
                        isImportant = important,
                        createdAt = now,
                        updatedAt = now
                    )
                    onSubmit(todo)
                }
            }) {
                Text("Save")
            }

            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
