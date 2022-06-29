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
import com.jeong.android.todo_mvvm.model.TodoModel

class TodoAfterAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var afterItems: List<TodoModel> = listOf()
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rec_after, parent, false)
        return TodoAfterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val todoModel = afterItems[position]
        val afterViewHolder = holder as TodoAfterViewHolder
        afterViewHolder.bind(todoModel)

    }

    override fun getItemCount(): Int {
        return afterItems.size
    }

    fun setAfterItems(afterItems: List<TodoModel>) {
        this.afterItems = afterItems
        notifyDataSetChanged()
    }

    inner class TodoAfterViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val todoText = itemView.findViewById<TextView>(R.id.tv_todo)
        val imageButtonRectangle = itemView.findViewById<ImageButton>(R.id.btn_check)
        fun bind(item: TodoModel) {
            todoText?.text = item.description
            todoText.paintFlags = todoText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            todoText.setTextColor(Color.parseColor("#808080"))

            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                imageButtonRectangle?.setOnClickListener {
                    listener?.onItemClick(itemView, item, pos)
                }
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(v:View, data: TodoModel, pos: Int)
    }

    fun setOnItemClick(listener: OnItemClickListener) {
        this.listener = listener
    }
}