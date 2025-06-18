package com.josip.minitodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.josip.minitodo.data.AppDatabase
import com.josip.minitodo.ui.AddTodoScreen
import com.josip.minitodo.ui.AddNoteScreen
import com.josip.minitodo.ui.EditTodoScreen
import com.josip.minitodo.ui.MainScreen
import com.josip.minitodo.viewmodel.NoteViewModel
import com.josip.minitodo.viewmodel.NoteViewModelFactory
import com.josip.minitodo.viewmodel.TodoViewModel
import com.josip.minitodo.viewmodel.TodoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = AppDatabase.getDatabase(this).todoDao()
        val daoNote = AppDatabase.getDatabase(this).noteDao()
        val factory = TodoViewModelFactory(dao)
        val factoryNote = NoteViewModelFactory(daoNote)

        setContent {
            val navController = rememberNavController()
            val viewModel: TodoViewModel = viewModel(factory = factory)
            val viewModelNote: NoteViewModel = viewModel(factory = factoryNote)

            NavHost(navController, startDestination = "main") {
                composable("main") {
                    MainScreen(
                        viewModel = viewModel,
                        viewModelNotes = viewModelNote,
                        onEditTodo = { todoId ->
                            navController.navigate("edit/$todoId")
                        },
                        onAddTodo = {
                            navController.navigate("add")
                        },
                        onAddNote = {
                            navController.navigate("addnote")
                        },
                    )
                }

                composable("edit/{todoId}", arguments = listOf(
                    navArgument("todoId") { type = NavType.IntType }
                )) { backStackEntry ->
                    val todoId = backStackEntry.arguments?.getInt("todoId") ?: return@composable
                    EditTodoScreen(todoId, viewModel, onNavigateBack = {
                        navController.popBackStack()
                    })
                }

                composable("add") {
                    AddTodoScreen(viewModel, onNavigateBack = {
                        navController.popBackStack()
                    })
                }

                composable("addnote") {
                    AddNoteScreen(viewModelNote, onNavigateBack = {
                        navController.popBackStack()
                    })
                }
            }
        }

    }
}