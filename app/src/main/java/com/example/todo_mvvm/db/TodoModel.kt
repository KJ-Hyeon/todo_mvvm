package com.example.todo_mvvm.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo")
data class TodoModel(
    @PrimaryKey
    val todo: String,
    val description: String,
    val Folder: String,
    val start_day: String,
    val end_day: String
)
