package com.example.todo_mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo_mvvm.db.TodoDao
import com.example.todo_mvvm.db.TodoDatabase
import com.example.todo_mvvm.repository.TodoDataSourceImpl
import com.example.todo_mvvm.repository.TodoRepository
import com.google.gson.Gson

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            val todoDataSourceImpl = TodoDataSourceImpl(TodoDatabase.getInstance(context, Gson()).todoDao())
            return TodoViewModel(TodoRepository(todoDataSourceImpl)) as T
        }
        else {
            throw IllegalAccessException("Failed to create ViewModel ${modelClass.name}")
        }
    }
}