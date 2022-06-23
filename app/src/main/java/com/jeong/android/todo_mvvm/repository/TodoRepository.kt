package com.jeong.android.todo_mvvm.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.jeong.android.todo_mvvm.database.TodoDao
import com.jeong.android.todo_mvvm.database.TodoDatabase
import com.jeong.android.todo_mvvm.model.TodoModel

class TodoRepository(application: Application) {
    private var mTodoDatabase: TodoDatabase
    private var mTodoDao: TodoDao
    private var mTodoItems: LiveData<List<TodoModel>>
    private var mTodoItemsMiddle: LiveData<List<TodoModel>>
    private var mTodoItemsAfter: LiveData<List<TodoModel>>

    init {
        mTodoDatabase = TodoDatabase.getInstance(application)
        mTodoDao = mTodoDatabase.todoDao()
        mTodoItems = mTodoDao.getTodoBeforeList()
        mTodoItemsMiddle = mTodoDao.getTodoMiddleList()
        mTodoItemsAfter = mTodoDao.getTodoAfterList()
    }

}