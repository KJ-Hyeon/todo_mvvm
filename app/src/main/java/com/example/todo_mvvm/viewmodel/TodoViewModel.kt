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
            _todoList.postValue(todoItem)
            Log.e("ViewModel_Init:","todoItem:$todoItem")
            Log.e("ViewModel_Init:","_todoList:$_todoList")
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

    fun addTodo(todo: String, description: String, folder: String, startDay: String, endDay: String) {
        val todoModel = TodoModel(todo, description, folder, startDay, endDay)
        viewModelScope.launch {
            todoRepository.addTodo(todoModel)
            todoItem.add(todoModel)
            _todoList.postValue(todoItem)
            Log.e("ViewModel_addTodo:","todoItem:$todoItem")
            Log.e("ViewModel_addTodo:","_todoList:$_todoList")
        }
    }
}