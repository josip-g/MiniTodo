package com.josip.minitodo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.josip.minitodo.common.LocaleHelper
import com.josip.minitodo.data.AppDatabase
import com.josip.minitodo.ui.design.MiniTodoTheme
import com.josip.minitodo.ui.navigation.MiniTodoNavGraph
import com.josip.minitodo.viewmodel.note.NoteViewModel
import com.josip.minitodo.viewmodel.note.NoteViewModelFactory
import com.josip.minitodo.viewmodel.task.TaskViewModel
import com.josip.minitodo.viewmodel.task.TaskViewModelFactory
import java.util.*

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val context = LocaleHelper.applySavedLocale(newBase)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase.getDatabase(this).taskDao()
        val daoNote = AppDatabase.getDatabase(this).noteDao()
        val factory = TaskViewModelFactory(dao)
        val factoryNote = NoteViewModelFactory(daoNote)

        setContent {
            val context = LocalContext.current
            val prefsLang = LocaleHelper.getSavedLanguage(this)
            var selectedLocale by remember { mutableStateOf(Locale(prefsLang)) }
            val localizedContext = LocaleHelper.wrapWithLocale(this, selectedLocale)

            val navController = rememberNavController()
            val taskViewModel: TaskViewModel = viewModel(factory = factory)
            val noteViewModel: NoteViewModel = viewModel(factory = factoryNote)

            CompositionLocalProvider(LocalContext provides localizedContext) {
                MiniTodoTheme {
                    MiniTodoNavGraph(
                        navController = navController,
                        taskViewModel = taskViewModel,
                        noteViewModel = noteViewModel,
                        selectedLocale = selectedLocale,
                        onLangChange = { newLang ->
                            selectedLocale = Locale(newLang)
                            LocaleHelper.saveLanguage(context, newLang)
                        }
                    )
                }
            }
        }
    }
}