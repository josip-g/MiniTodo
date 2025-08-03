package com.josip.minitodo.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.josip.minitodo.ui.screens.*
import com.josip.minitodo.viewmodel.note.NoteViewModel
import com.josip.minitodo.viewmodel.task.TaskViewModel
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MiniTodoNavGraph(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    noteViewModel: NoteViewModel,
    selectedLocale: Locale,
    onLangChange: (String) -> Unit
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        enterTransition = { NavigationTransitions.enter },
        exitTransition = { NavigationTransitions.exit },
        popEnterTransition = { NavigationTransitions.popEnter },
        popExitTransition = { NavigationTransitions.popExit }
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                viewModel = taskViewModel,
                onEditTask = { taskId -> navController.navigateToEditTask(taskId) },
                onAddTask = { navController.navigateToAddTask() },
                onGetNotes = { navController.navigate(Screen.Notes.route) },
                currentLang = selectedLocale.language,
                onLanguageChange = { newLang -> onLangChange(newLang) }
            )
        }

        composable(
            Screen.EditTask.route,
            arguments = listOf(navArgument(NavArgs.TASK_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt(NavArgs.TASK_ID) ?: return@composable
            EditTaskScreen(taskId, taskViewModel) { navController.popBackStack() }
        }

        composable(Screen.AddTask.route) {
            AddTaskScreen(taskViewModel) { navController.popBackStack() }
        }

        composable(Screen.AddNote.route) {
            AddNoteScreen(noteViewModel) { navController.popBackStack() }
        }

        composable(
            Screen.EditNote.route,
            arguments = listOf(navArgument(NavArgs.NOTE_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt(NavArgs.NOTE_ID) ?: return@composable
            EditNoteScreen(noteId, noteViewModel) { navController.popBackStack() }
        }

        composable(Screen.Notes.route) {
            NotesScreen(
                viewModelNotes = noteViewModel,
                onAddNote = { navController.navigateToAddNote() },
                onEditNote = { noteId -> navController.navigateToEditNote(noteId) },
            )
        }
    }
}