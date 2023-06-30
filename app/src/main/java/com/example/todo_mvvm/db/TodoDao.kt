package com.example.todo_mvvm.db

import androidx.room.*

@Dao
interface TodoDao {

    @Query("SELECT * FROM Todo")
    suspend fun getAllTodo(): List<TodoModel>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todo: TodoModel)

    @Update
    suspend fun updateCheck(todoModel: TodoModel)
}