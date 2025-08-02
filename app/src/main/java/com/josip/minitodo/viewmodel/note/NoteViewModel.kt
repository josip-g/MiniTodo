package com.josip.minitodo.viewmodel.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josip.minitodo.data.model.Note
import com.josip.minitodo.data.dao.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(private val dao: NoteDao) : ViewModel() {
    val notes = dao.getAllNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun addNote(note: Note) {
        viewModelScope.launch {
            dao.insert(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            dao.delete(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            val updatedNote = note.copy(updatedAt = System.currentTimeMillis())
            dao.updateNote(updatedNote)
        }
    }

    fun getNoteById(id: Int): Flow<Note?> {
        return dao.getById(id)
    }
}