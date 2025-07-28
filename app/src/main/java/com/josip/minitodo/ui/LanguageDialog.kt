package com.josip.minitodo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
        title = { Text(getDialogTitleForLang(currentLang)) },
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

fun getDialogTitleForLang(lang: String): String {
    return when (lang) {
        "de" -> "Sprache wählen"
        "hr" -> "Odaberi jezik"
        else -> "Choose language"
    }
}