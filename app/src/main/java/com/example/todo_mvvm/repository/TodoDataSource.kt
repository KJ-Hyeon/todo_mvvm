package com.example.todo_mvvm.repository

import com.example.todo_mvvm.db.TodoModel

interface TodoDataSource {

    suspend fun getAllTodo(): List<TodoModel>
    suspend fun addTodo(todo: TodoModel)
    suspend fun updateCheck(todoModel: TodoModel)
}