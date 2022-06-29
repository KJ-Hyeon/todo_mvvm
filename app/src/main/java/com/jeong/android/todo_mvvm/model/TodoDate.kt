package com.jeong.android.todo_mvvm.model

import androidx.room.Entity

@Entity(tableName = "Date")
data class TodoDate (
    val date: String,
    val todoModel : List<TodoModel>
        )