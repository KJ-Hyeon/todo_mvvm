package com.example.todo_mvvm.repository

import com.example.todo_mvvm.db.TodoModel

interface TodoDataSource {

    suspend fun addTodo(todo: TodoModel)
}