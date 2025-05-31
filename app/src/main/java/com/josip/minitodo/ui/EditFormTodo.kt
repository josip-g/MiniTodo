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

@Composable
fun EditTodoForm(
    todo: Todo,
    onSave: (Todo) -> Unit,
    onCancel: () -> Unit
) {
    var text by remember { mutableStateOf(todo.text) }
    var important by remember { mutableStateOf(todo.isImportant) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Uredi zadatak", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Zadatak") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = important,
                onCheckedChange = { important = it }
            )
            Text("Važno")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                if (text.isNotBlank()) {
                    onSave(todo.copy(text = text, isImportant = important))
                }
            }) {
                Text("Spremi")
            }

            OutlinedButton(onClick = onCancel) {
                Text("Odustani")
            }
        }
    }
}