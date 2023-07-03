package com.example.todo_mvvm.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_mvvm.R
import com.example.todo_mvvm.databinding.ItemTodoBinding
import com.example.todo_mvvm.db.TodoModel

class TodoListAdapter: ListAdapter<TodoModel, TodoListAdapter.TodoViewHolder>(diffUtil) {

    private lateinit var binding: ItemTodoBinding
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun checkboxClick(v: View, todoModel: TodoModel)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_todo, parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun strikeThrough(todoModel: TodoModel) {
        val position = currentList.indexOf(todoModel)
        notifyItemChanged(position)
//        submitList(currentList)
    }

    inner class TodoViewHolder(private val binding: ItemTodoBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoModel) {
            binding.item = item

            binding.todoCheck.setOnClickListener {
                val check = binding.todoCheck.isChecked
                item.check = check
                listener?.checkboxClick(it, item)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TodoModel>() {
            override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
                return oldItem.todo == newItem.todo
            }

            override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}