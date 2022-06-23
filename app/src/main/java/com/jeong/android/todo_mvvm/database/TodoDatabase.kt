package com.jeong.android.todo_mvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jeong.android.todo_mvvm.model.TodoModel

@Database(entities = [TodoModel::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    // APP이 실행되는 동안 한번만 실행되는?
    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        //        fun getInstance(context: Context): TodoDatabase? {
//            if (INSTANCE == null) {
//                synchronized(TodoDatabase::class){
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        TodoDatabase::class.java,
//                        "Todo.db"
//                    ).build()
//                }
//            }
//            return INSTANCE
//        }
        fun getInstance(context: Context): TodoDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "Todo.db"
            ).build()
    }
}