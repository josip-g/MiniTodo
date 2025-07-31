package com.josip.minitodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josip.minitodo.data.Todo
import com.josip.minitodo.data.TodoDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(private val dao: TodoDao) : ViewModel() {
    val todos = dao.getAllTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTodo(text: String, important: Boolean, createdAt: Long, updatedAt: Long) {
        viewModelScope.launch {
            dao.insertTodo(Todo(text = text, isImportant = important, createdAt = createdAt, updatedAt = updatedAt))
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            dao.deleteTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            val updatedTodo = todo.copy(updatedAt = System.currentTimeMillis())
            dao.updateTodo(updatedTodo)
        }
    }

    fun getTodoById(id: Int): Flow<Todo?> {
        return dao.getById(id)
    }

    fun toggleTodoDone(todo: Todo) {
        viewModelScope.launch {
            val updatedTodo = todo.copy(isDone = !todo.isDone, updatedAt = System.currentTimeMillis())
            dao.updateTodo(updatedTodo)
        }
    }
}