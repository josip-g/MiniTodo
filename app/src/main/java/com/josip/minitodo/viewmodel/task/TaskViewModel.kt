package com.josip.minitodo.viewmodel.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josip.minitodo.data.dao.TaskDao
import com.josip.minitodo.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val dao: TaskDao) : ViewModel() {
    val tasks = dao.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTask(text: String, important: Boolean, createdAt: Long, updatedAt: Long) {
        viewModelScope.launch {
            dao.insertTask(Task(text = text, isImportant = important, createdAt = createdAt, updatedAt = updatedAt))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            dao.deleteTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(updatedAt = System.currentTimeMillis())
            dao.updateTask(updatedTask)
        }
    }

    fun getTaskById(id: Int): Flow<Task?> {
        return dao.getById(id)
    }

    fun toggleTaskDone(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(isDone = !task.isDone, updatedAt = System.currentTimeMillis())
            dao.updateTask(updatedTask)
        }
    }
}