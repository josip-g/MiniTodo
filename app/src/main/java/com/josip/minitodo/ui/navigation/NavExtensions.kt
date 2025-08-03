package com.josip.minitodo.ui.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToEditTask(taskId: Int) =
    navigate(Screen.EditTask.createRoute(taskId))

fun NavHostController.navigateToEditNote(noteId: Int) =
    navigate(Screen.EditNote.createRoute(noteId))

fun NavHostController.navigateToAddTask() =
    navigate(Screen.AddTask.route)

fun NavHostController.navigateToAddNote() =
    navigate(Screen.AddNote.route)