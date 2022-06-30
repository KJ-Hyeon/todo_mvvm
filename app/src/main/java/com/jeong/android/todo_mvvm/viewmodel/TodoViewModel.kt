package com.jeong.android.todo_mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jeong.android.todo_mvvm.common.SingleLiveEvent
import com.jeong.android.todo_mvvm.model.TodoListItem
import com.jeong.android.todo_mvvm.model.TodoModel
import com.jeong.android.todo_mvvm.repository.TodoRepository
import java.text.SimpleDateFormat

class TodoViewModel(application: Application): AndroidViewModel(application) {
    private val mTodoRepository: TodoRepository
//    private var mTodoItems: LiveData<List<TodoModel>>
//    private var mTodoItemsMiddle: LiveData<List<TodoModel>>
//    private var mTodoItemsAfter: LiveData<List<TodoModel>>
//    private val todoLiveData = MutableLiveData<List<TodoListItem>>()
//    val todos: LiveData<List<TodoListItem>> = todoLiveData
    val text = MutableLiveData("")
    val addBtnClickEvent = SingleLiveEvent<Any>()

    init {
        mTodoRepository = TodoRepository(application)
//        mTodoItems = mTodoRepository.getTodoBeforeList()
//        mTodoItemsMiddle = mTodoRepository.getTodoMiddleList()
//        mTodoItemsAfter = mTodoRepository.getTodoAfterList()
    }
    fun insertTodo(todoModel: TodoModel) {
        mTodoRepository.insertTodo(todoModel)
    }
    fun deleteTodo(todoModel: TodoModel) {
        mTodoRepository.deleteTodo(todoModel)
    }
    fun getTodoBeforeList(): LiveData<List<TodoModel>> {
        return mTodoRepository.getTodoBeforeList()
    }
    fun getTodoMiddleList(): LiveData<List<TodoModel>> {
        return mTodoRepository.getTodoMiddleList()
    }
    fun getTodoAfterList(): LiveData<List<TodoModel>> {
        return mTodoRepository.getTodoAfterList()
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
    fun onClickAddButton() {
        addBtnClickEvent.call()
    }

    private fun List<TodoModel>.toListItem() : List<TodoListItem> {
        val result = arrayListOf<TodoListItem>()
        val date = currentDate()
        var headerDate = date
        this.forEach { todo ->
            if (headerDate != todo.date) {
                result.add(TodoListItem.Header(todo))
            }
            result.add(TodoListItem.Item(todo))
            headerDate  = todo.date
        }
        return result
    }

    fun currentDate(): String {
        val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(currentTime)
    }

}













