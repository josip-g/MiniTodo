package com.josip.minitodo.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.josip.minitodo.common.LocaleHelper
import com.josip.minitodo.common.LocaleHelper.getLanguages

@Composable
fun LanguageDialog(
    currentLang: String,
    applyButtonText: String,
    cancelButtonText: String,
    onLanguageChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedLang by remember { mutableStateOf(currentLang) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(LocaleHelper.getDialogTitleForLang(context)) },
        text = {
            Column {
                val languages = getLanguages(context)
                languages.forEach { (code, name) ->
                    val isSelected = selectedLang == code
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                else Color.Transparent
                            )
                            .padding(12.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                selectedLang = code
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
        confirmButton = {
            Button(onClick = {
                onLanguageChange(selectedLang)
                onDismiss()
            }) {
                Text(applyButtonText)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = { onDismiss() }) {
                Text(cancelButtonText)
            }
        }
    )
}