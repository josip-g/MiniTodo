package com.josip.minitodo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreenFABs(onAddTask: () -> Unit, onGetNotes: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = onAddTask,
            modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 24.dp, end = 24.dp).navigationBarsPadding()
        ) { Icon(Icons.Default.AddTask, contentDescription = null) }
        FloatingActionButton(
            onClick = onGetNotes,
            modifier = Modifier.align(Alignment.BottomStart).padding(bottom = 24.dp, start = 24.dp).navigationBarsPadding()
        ) { Icon(Icons.Default.LibraryBooks, contentDescription = null) }
    }
}