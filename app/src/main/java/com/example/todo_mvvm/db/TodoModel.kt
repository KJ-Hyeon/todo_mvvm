package com.example.todo_mvvm.db

import androidx.room.Entity

@Entity(tableName = "Todo")
data class TodoModel(
    val todo: String,
    val description: String,
    val Folder: String,
    val start_day: String,
    val end_day: String
)
