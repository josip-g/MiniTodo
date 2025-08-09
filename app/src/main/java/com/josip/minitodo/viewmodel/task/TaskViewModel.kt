package com.josip.minitodo.viewmodel.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josip.minitodo.data.dao.TaskDao
import com.josip.minitodo.data.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class TaskUiState(
    val isLoading: Boolean = true,
    val tasks: List<Task> = emptyList()
)

class TaskViewModel(private val dao: TaskDao) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState

    init {
        viewModelScope.launch {
            dao.getAllTasks().collect { list ->
                _uiState.value = TaskUiState(
                    isLoading = false,
                    tasks = list
                )
            }
        }
    }

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

    fun getTaskById(id: Int) = dao.getById(id)

    fun toggleTaskDone(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(isDone = !task.isDone, updatedAt = System.currentTimeMillis())
            dao.updateTask(updatedTask)
        }
    }
}