package com.example.todo_mvvm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.todo_mvvm.R
import com.example.todo_mvvm.databinding.FragmentHomeBinding
import com.example.todo_mvvm.db.TodoModel
import com.example.todo_mvvm.viewmodel.TodoViewModel
import com.example.todo_mvvm.viewmodel.ViewModelFactory

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding
//    private lateinit var todoViewModel: TodoViewModel
    private val todoViewModel: TodoViewModel by activityViewModels { ViewModelFactory(requireContext()) }
    private val todoAdapter = TodoListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
//        todoViewModel = ViewModelProvider(this, ViewModelFactory(requireContext()))[TodoViewModel::class.java]
        // ListAdapter에서 notifyItemChanged를 호출할때, 깜빡거림 현상을 방지하기 위해서
        val animator = binding.homeRecyclerView.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
        binding.homeRecyclerView.adapter = todoAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoViewModel.todoList.observe(viewLifecycleOwner) {
            todoAdapter.submitList(it)
        }

        todoViewModel.checkState.observe(viewLifecycleOwner) {
            // checkbox의 상태를 실시간으로 관찰?
            todoAdapter.strikeThrough(it)
        }

        todoAdapter.setOnItemClickListener(object : TodoListAdapter.OnItemClickListener {
            override fun checkboxClick(v: View, todoModel: TodoModel) {
                todoViewModel.todoCheckUpdate(todoModel)
            }
        })
    }
}