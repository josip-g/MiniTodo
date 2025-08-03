package com.josip.minitodo.viewmodel.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.josip.minitodo.data.dao.TaskDao

class TaskViewModelFactory(private val dao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(dao) as T
    }
}