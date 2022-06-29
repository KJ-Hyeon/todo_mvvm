package com.jeong.android.todo_mvvm.ui

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jeong.android.todo_mvvm.R
import com.jeong.android.todo_mvvm.databinding.ItemRecAfterBinding
import com.jeong.android.todo_mvvm.databinding.ItemRecBinding
import com.jeong.android.todo_mvvm.model.TodoModel

class TodoAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var todoItems: List<TodoModel> = listOf()
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val view = when(viewType) {
//            1 -> LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_rec_after, parent, false)
//            else -> LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_rec, parent, false)
//        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rec, parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val todoModel = todoItems[position]
        return if (todoModel.status == "AFTER") 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val todoModel = todoItems[position]
        val todoViewHolder = holder as TodoViewHolder
        todoViewHolder.bind(todoModel)
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val todoText = itemView.findViewById<TextView>(R.id.tv_todo)
        val imageButtonRectangle = itemView.findViewById<ImageButton>(R.id.btn_check)
        fun bind(item: TodoModel) {
            todoText?.text = item.description
            if (itemViewType == 1) {
                todoText.paintFlags = todoText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                todoText.setTextColor(Color.parseColor("#808080"))
            }
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                imageButtonRectangle?.setOnClickListener {
                    listener?.onItemClick(itemView, item, pos)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(v:View, data: TodoModel, pos: Int)
    }

    fun setOnClickListenser(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setTodoItems(todoItems: List<TodoModel>) {
        this.todoItems = todoItems
        notifyDataSetChanged()
    }

}