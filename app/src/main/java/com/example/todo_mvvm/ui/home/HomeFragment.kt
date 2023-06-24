package com.example.todo_mvvm.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.todo_mvvm.R
import com.example.todo_mvvm.databinding.FragmentHomeBinding
import com.example.todo_mvvm.viewmodel.TodoViewModel
import com.example.todo_mvvm.viewmodel.ViewModelFactory

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding
//    private lateinit var todoViewModel: TodoViewModel
    private val todoViewModel: TodoViewModel by activityViewModels { ViewModelFactory(requireContext()) }
    private val categoryAdapter = CategoryListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
//        todoViewModel = ViewModelProvider(this, ViewModelFactory(requireContext()))[TodoViewModel::class.java]
        binding.homeRecyclerView.adapter = categoryAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoViewModel.todoList.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
        }
    }
}