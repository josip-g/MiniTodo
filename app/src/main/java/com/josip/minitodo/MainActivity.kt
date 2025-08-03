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
import com.josip.minitodo.ui.screens.AddTaskScreen
import com.josip.minitodo.ui.screens.EditNoteScreen
import com.josip.minitodo.ui.screens.EditTaskScreen
import com.josip.minitodo.ui.screens.MainScreen
import com.josip.minitodo.ui.screens.NotesScreen
import com.josip.minitodo.common.LocaleHelper
import com.josip.minitodo.ui.navigation.Screen
import com.josip.minitodo.viewmodel.note.NoteViewModel
import com.josip.minitodo.viewmodel.note.NoteViewModelFactory
import com.josip.minitodo.viewmodel.task.TaskViewModel
import com.josip.minitodo.viewmodel.task.TaskViewModelFactory
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
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
            val viewModel: TaskViewModel = viewModel(factory = factory)
            val viewModelNote: NoteViewModel = viewModel(factory = factoryNote)

            CompositionLocalProvider(
                LocalContext provides localizedContext
            ) {
                MiniTodoTheme {
                    AnimatedNavHost(
                        navController,
                        startDestination = Screen.Main.route,
                        enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) + fadeIn() },
                        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) + fadeOut() },
                        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(300)) + fadeIn() },
                        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300)) + fadeOut() }
                    ) {
                        composable(Screen.Main.route) {
                            MainScreen(
                                viewModel = viewModel,
                                onEditTask = { taskId -> navController.navigate(Screen.EditTask.createRoute(taskId)) },
                                onAddTask = { navController.navigate(Screen.AddTask.route) },
                                onGetNotes = { navController.navigate(Screen.Notes.route) },
                                currentLang = selectedLocale.language,
                                onLanguageChange = { newLang ->
                                    selectedLocale = Locale(newLang)
                                    LocaleHelper.saveLanguage(context, newLang)
                                }
                            )
                        }

                        composable(
                            Screen.EditTask.route,
                            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getInt("taskId") ?: return@composable
                            EditTaskScreen(taskId, viewModel) { navController.popBackStack() }
                        }

                        composable(Screen.AddTask.route) {
                            AddTaskScreen(viewModel) { navController.popBackStack() }
                        }

                        composable(Screen.AddNote.route) {
                            AddNoteScreen(viewModelNote) { navController.popBackStack() }
                        }

                        composable(
                            Screen.EditNote.route,
                            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val noteId = backStackEntry.arguments?.getInt("noteId") ?: return@composable
                            EditNoteScreen(noteId, viewModelNote) { navController.popBackStack() }
                        }

                        composable(Screen.Notes.route) {
                            NotesScreen(
                                viewModelNotes = viewModelNote,
                                onAddNote = { navController.navigate(Screen.AddNote.route) },
                                onEditNote = { noteId -> navController.navigate(Screen.EditNote.createRoute(noteId)) },
                            )
                        }
                    }
                }
            }
        }
    }
}