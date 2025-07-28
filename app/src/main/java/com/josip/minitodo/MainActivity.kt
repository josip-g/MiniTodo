package com.josip.minitodo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import com.josip.minitodo.data.AppDatabase
import com.josip.minitodo.ui.*
import com.josip.minitodo.ui.theme.MiniTodoTheme
import com.josip.minitodo.viewmodel.*
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.ui.platform.LocalContext
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val context = com.josip.minitodo.utils.LocaleHelper.applySavedLocale(newBase)
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
            val prefsLang = com.josip.minitodo.utils.LocaleHelper.getSavedLanguage(this)
            var selectedLocale by remember { mutableStateOf(Locale(prefsLang)) }
            val localizedContext = com.josip.minitodo.utils.LocaleHelper.wrapWithLocale(this, selectedLocale)

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
                                    com.josip.minitodo.utils.LocaleHelper.saveLanguage(context, newLang)
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