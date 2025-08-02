package com.josip.minitodo.viewmodel.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.josip.minitodo.data.dao.NoteDao

class NoteViewModelFactory(private val dao: NoteDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(dao) as T
    }
}
