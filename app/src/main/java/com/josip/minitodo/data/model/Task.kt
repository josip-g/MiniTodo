package com.josip.minitodo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val isImportant: Boolean,
    val isDone: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
)
