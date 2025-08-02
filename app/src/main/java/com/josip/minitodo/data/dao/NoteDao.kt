package com.josip.minitodo.data.dao

import androidx.room.*
import com.josip.minitodo.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note ORDER BY createdAt DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM Note WHERE id = :id")
    fun getById(id: Int): Flow<Note?>
}