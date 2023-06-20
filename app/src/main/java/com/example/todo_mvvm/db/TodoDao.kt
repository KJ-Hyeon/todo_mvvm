package com.example.todo_mvvm.db

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TodoDao {

    @Insert
    fun addTodo(todo: TodoModel)
}