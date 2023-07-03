package com.example.todo_mvvm.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_mvvm.Event
import com.example.todo_mvvm.db.TodoModel
import com.example.todo_mvvm.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val todoRepository: TodoRepository): ViewModel() {

    private var _todoList = MutableLiveData<List<TodoModel>>()
    val todoList: LiveData<List<TodoModel>> = _todoList
    private var todoItem : MutableList<TodoModel> = mutableListOf()

    private var _addTodoEvent = MutableLiveData<Event<Boolean>>()
    var addTodoEvent: LiveData<Event<Boolean>> = _addTodoEvent

    private var _addErrorTodoEvent = MutableLiveData<Event<Boolean>>()
    var addErrorTodoEvent: LiveData<Event<Boolean>> = _addErrorTodoEvent

    var checkState = MutableLiveData<TodoModel>()

    init {
        viewModelScope.launch {
            todoItem = todoRepository.getAllTodo().toMutableList()
            _todoList.postValue(todoItem)
        }
    }

    fun addTodo(todo: String, description: String, folder: String, startDay: String, endDay: String) {
        if (todo.isEmpty() || description.isEmpty() || folder.isEmpty() || startDay.isEmpty() || endDay.isEmpty()) {
            _addErrorTodoEvent.value = Event(true)
        } else {
            val todoModel = TodoModel(todo, description, folder, startDay, endDay, false)
            viewModelScope.launch {
                todoRepository.addTodo(todoModel)
                todoItem.add(todoModel)
                _todoList.postValue(todoItem)
            }
            _addTodoEvent.value = Event(true)
        }
    }

    fun todoCheckUpdate(todoModel: TodoModel) {
        viewModelScope.launch {
            todoRepository.updateCheck(todoModel)
            checkState.postValue(todoModel)
        }
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