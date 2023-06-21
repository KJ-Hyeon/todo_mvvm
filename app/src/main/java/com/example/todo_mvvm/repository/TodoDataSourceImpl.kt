package com.example.todo_mvvm.repository

import com.example.todo_mvvm.db.TodoDao
import com.example.todo_mvvm.db.TodoModel

class TodoDataSourceImpl(private val dao: TodoDao): TodoDataSource {

    override suspend fun getAllTodo(): List<TodoModel> {
        return dao.getAllTodo()
    }
    override suspend fun addTodo(todo: TodoModel) {
        dao.addTodo(todo)
    }
}