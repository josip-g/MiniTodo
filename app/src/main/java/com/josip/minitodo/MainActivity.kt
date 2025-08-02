package com.josip.minitodo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import com.josip.minitodo.data.AppDatabase
import com.josip.minitodo.ui.design.MiniTodoTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.ui.platform.LocalContext
import com.josip.minitodo.ui.screens.AddNoteScreen
import com.josip.minitodo.ui.screens.AddTodoScreen
import com.josip.minitodo.ui.screens.EditNoteScreen
import com.josip.minitodo.ui.screens.EditTodoScreen
import com.josip.minitodo.ui.screens.MainScreen
import com.josip.minitodo.ui.screens.NotesScreen
import com.josip.minitodo.common.LocaleHelper
import com.josip.minitodo.viewmodel.note.NoteViewModel
import com.josip.minitodo.viewmodel.note.NoteViewModelFactory
import com.josip.minitodo.viewmodel.todo.TodoViewModel
import com.josip.minitodo.viewmodel.todo.TodoViewModelFactory
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val context = com.josip.minitodo.common.LocaleHelper.applySavedLocale(newBase)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase.getDatabase(this).todoDao()
        val daoNote = AppDatabase.getDatabase(this).noteDao()
        val factory = TodoViewModelFactory(dao)
        val factoryNote = NoteViewModelFactory(daoNote)

        setContent {
            val context = LocalContext.current
            val prefsLang = com.josip.minitodo.common.LocaleHelper.getSavedLanguage(this)
            var selectedLocale by remember { mutableStateOf(Locale(prefsLang)) }
            val localizedContext = com.josip.minitodo.common.LocaleHelper.wrapWithLocale(this, selectedLocale)

            val navController = rememberNavController()
            val viewModel: TodoViewModel = viewModel(factory = factory)
            val viewModelNote: NoteViewModel = viewModel(factory = factoryNote)

            CompositionLocalProvider(
                LocalContext provides localizedContext
            ) {
                MiniTodoTheme {
                    AnimatedNavHost(
                        navController,
                        startDestination = "main",
                        enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) + fadeIn() },
                        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) + fadeOut() },
                        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(300)) + fadeIn() },
                        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300)) + fadeOut() }
                    ) {
                        composable("main") {
                            MainScreen(
                                viewModel = viewModel,
                                onEditTodo = { todoId -> navController.navigate("edit/$todoId") },
                                onAddTodo = { navController.navigate("add") },
                                onGetNotes = { navController.navigate("getnotes") },
                                currentLang = selectedLocale.language,
                                onLanguageChange = { newLang ->
                                    selectedLocale = Locale(newLang)
                                    LocaleHelper.saveLanguage(context, newLang)
                                }
                            )
                        }

                        composable(
                            "edit/{todoId}",
                            arguments = listOf(navArgument("todoId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val todoId = backStackEntry.arguments?.getInt("todoId") ?: return@composable
                            EditTodoScreen(todoId, viewModel) { navController.popBackStack() }
                        }

                        composable("add") {
                            AddTodoScreen(viewModel) { navController.popBackStack() }
                        }

                        composable("addnote") {
                            AddNoteScreen(viewModelNote) { navController.popBackStack() }
                        }

                        composable(
                            "editnote/{noteId}",
                            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val noteId = backStackEntry.arguments?.getInt("noteId") ?: return@composable
                            EditNoteScreen(noteId, viewModelNote) { navController.popBackStack() }
                        }

                        composable("getnotes") {
                            NotesScreen(
                                viewModelNotes = viewModelNote,
                                onAddNote = { navController.navigate("addnote") },
                                onEditNote = { noteId -> navController.navigate("editnote/$noteId") },
                            )
                        }
                    }
                }
            }
        }
    }
}