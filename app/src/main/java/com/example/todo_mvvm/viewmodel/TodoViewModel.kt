package com.example.todo_mvvm.viewmodel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_mvvm.db.TodoModel
import com.example.todo_mvvm.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val todoRepository: TodoRepository): ViewModel() {

    private var _todoList = MutableLiveData<List<TodoModel>>()
    val todoList: LiveData<List<TodoModel>> = _todoList
    private var todoItem : MutableList<TodoModel> = mutableListOf()

    init {
        viewModelScope.launch {
            todoItem = todoRepository.getAllTodo().toMutableList()
            _todoList.value = todoItem
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            _todo.postValue(todoRepository.getAllTodo())
//        }
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                _todo.postValue(todoRepository.getAllTodo())
//            }
//        }
//        viewModelScope.launch(Dispatchers.IO) {
//            _todo.postValue(todoRepository.getAllTodo())
//        }

    }

    fun addTodo(todo: TodoModel) {
        viewModelScope.launch {
            Log.e("addTodo","${todo.todo}")
            todoRepository.addTodo(todo)
            todoItem.add(todo)
            _todoList.value = todoItem
        }
    }
}