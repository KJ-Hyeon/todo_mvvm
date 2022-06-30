package com.jeong.android.todo_mvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo")
data class TodoModel (
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "status")
    var status: String,
    var date : String
        )
