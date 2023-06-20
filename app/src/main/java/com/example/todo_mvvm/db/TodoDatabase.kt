package com.example.todo_mvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson

@Database(entities = [TodoModel::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        private var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context, gson: Gson): TodoDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context, gson).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context, gson: Gson) =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "Todo.db"
                )
                .build()
    }
}