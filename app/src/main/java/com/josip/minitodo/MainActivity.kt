package com.josip.minitodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.josip.minitodo.data.TodoDatabase
import com.josip.minitodo.ui.MainScreen
import com.josip.minitodo.viewmodel.TodoViewModel
import com.josip.minitodo.viewmodel.TodoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = TodoDatabase.getDatabase(this).todoDao()
        val factory = TodoViewModelFactory(dao)

        setContent {
            val viewModel: TodoViewModel = viewModel(factory = factory)
            MainScreen(viewModel)
        }
    }
}