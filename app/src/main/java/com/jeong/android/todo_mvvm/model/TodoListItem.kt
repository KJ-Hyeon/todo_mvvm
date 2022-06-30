package com.jeong.android.todo_mvvm.model

import com.jeong.android.todo_mvvm.R

sealed class TodoListItem {
    abstract val todo: TodoModel
    abstract val layoutId: Int

    data class Header(
        override val todo: TodoModel,
        override val layoutId: Int = VIEW_TYPE
    ) : TodoListItem() {
        companion object {
            const val VIEW_TYPE = R.layout.item_date_rcv
        }
    }

    data class Item(
        override val todo: TodoModel,
        override val layoutId: Int = VIEW_TYPE
    ) : TodoListItem() {
        companion object {
            const val VIEW_TYPE = R.layout.item_rec
        }
    }
}


