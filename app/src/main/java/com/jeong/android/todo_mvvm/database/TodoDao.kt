package com.jeong.android.todo_mvvm.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jeong.android.todo_mvvm.model.TodoModel

@Dao
interface TodoDao {
    @Query("SELECT * from Todo where status = 'BEFORE'")
    fun getTodoBeforeList() : LiveData<List<TodoModel>>
    @Insert
    fun insertTodo(todoModel: TodoModel)

    @Query("SELECT * from Todo where status = 'MIDDLE' ORDER BY Todo.date DESC")
    fun getTodoMiddleList() : LiveData<List<TodoModel>>

    @Query("UPDATE Todo SET status = 'MIDDLE' WHERE status = 'BEFORE' and id = :todoModelID")
    fun updateTodoBeforeToMiddle(todoModelID: Long?)

    @Query("UPDATE Todo SET status = 'AFTER' WHERE status = 'MIDDLE' and id = :todoModelID")
    fun updateTodoMiddleToAfter(todoModelID: Long?)

    @Query("SELECT * from Todo where status = 'AFTER'")
    fun getTodoAfterList() : LiveData<List<TodoModel>>

    @Delete
    fun deleteTodo(todoModel: TodoModel)

}
//interface TodoDao {
//    @Query("SELECT * from Todo where status = 'BEFORE'")
//    fun getTodoBeforeList() : List<TodoModel>
//    @Insert
//    fun insertTodo(todoModel: TodoModel)
//
//    @Query("SELECT * from Todo where status = 'MIDDLE'")
//    fun getTodoMiddleList() : List<TodoModel>
//
//    @Query("UPDATE Todo SET status = 'MIDDLE' WHERE status = 'BEFORE' and id = :todoModelID")
//    fun updateTodoBeforeToMiddle(todoModelID: Long?)
//
//    @Query("UPDATE Todo SET status = 'AFTER' WHERE status = 'MIDDLE' and id = :todoModelID")
//    fun updateTodoMiddleToAfter(todoModelID: Long?)
//
//    @Query("SELECT * from Todo where status = 'AFTER'")
//    fun getTodoAfterList() : List<TodoModel>
//
//    @Delete
//    fun deleteTodo(todoModel: TodoModel)
//
//}
