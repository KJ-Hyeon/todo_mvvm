package com.jeong.android.todo_mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jeong.android.todo_mvvm.model.TodoModel
import com.jeong.android.todo_mvvm.repository.TodoRepository

class TodoViewModel(application: Application): AndroidViewModel(application) {
    private val mTodoRepository: TodoRepository
    private var mTodoItems: LiveData<List<TodoModel>>
    private var mTodoItemsMiddle: LiveData<List<TodoModel>>
    private var mTodoItemsAfter: LiveData<List<TodoModel>>
    val text = MutableLiveData("")

    init {
        mTodoRepository = TodoRepository(application)
        mTodoItems = mTodoRepository.getTodoBeforeList()
        mTodoItemsMiddle = mTodoRepository.getTodoMiddleList()
        mTodoItemsAfter = mTodoRepository.getTodoAfterList()
    }
    fun insertTodo(todoModel: TodoModel) {
        mTodoRepository.insertTodo(todoModel)
    }
    fun deleteTodo(todoModel: TodoModel) {
        mTodoRepository.deleteTodo(todoModel)
    }
    fun getTodoBeforeList(): LiveData<List<TodoModel>> {
        return mTodoItems
    }
    fun getTodoMiddleList(): LiveData<List<TodoModel>> {
        return mTodoItemsMiddle
    }
    fun getTodoAfterList(): LiveData<List<TodoModel>> {
        return mTodoItemsAfter
    }
    fun updateTodoBeforeToMiddle(todoModelID: Long?) {
        mTodoRepository.updateTodoBeforeToMiddle(todoModelID)
    }
    fun updateTodoMiddleToAfter(todoModelId: Long?) {
        mTodoRepository.updateTodoMiddleToAfter(todoModelId)
    }
    fun deleteText() {
        text.value = null
    }
}