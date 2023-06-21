package com.example.todo_mvvm.repository

import com.example.todo_mvvm.db.TodoModel

class TodoRepository(private val todoDataSourceImpl: TodoDataSourceImpl) {

    suspend fun getAllTodo(): List<TodoModel> {
        return todoDataSourceImpl.getAllTodo()
    }

    suspend fun addTodo(todo:TodoModel) {
        todoDataSourceImpl.addTodo(todo)
    }

}