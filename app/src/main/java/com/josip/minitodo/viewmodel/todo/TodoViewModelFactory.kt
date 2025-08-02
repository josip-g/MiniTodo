package com.josip.minitodo.viewmodel.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.josip.minitodo.data.dao.TodoDao

class TodoViewModelFactory(private val dao: TodoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoViewModel(dao) as T
    }
}