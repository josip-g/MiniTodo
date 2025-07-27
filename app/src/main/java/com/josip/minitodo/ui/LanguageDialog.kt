package com.josip.minitodo.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color

@Composable
fun LanguageDialog(
    currentLang: String,
    onLanguageChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val languages = listOf(
        "en" to "English",
        "hr" to "Hrvatski",
        "de" to "Deutsch"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Choose language") },
        text = {
            Column {
                languages.forEach { (code, name) ->
                    val isSelected = currentLang == code
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                else Color.Transparent
                            )
                            .padding(12.dp)
                            .clickable {
                                onLanguageChange(code)
                                onDismiss()
                            }
                    ) {
                        Text(
                            text = name,
                            color = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        },
        confirmButton = {}
    )
}