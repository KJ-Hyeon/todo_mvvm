package com.jeong.android.todo_mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeong.android.todo_mvvm.R
import com.jeong.android.todo_mvvm.common.BaseFragment
import com.jeong.android.todo_mvvm.databinding.FragmentMiddleBinding
import com.jeong.android.todo_mvvm.model.TodoModel
import com.jeong.android.todo_mvvm.viewmodel.TodoViewModel

class MiddleFragment : BaseFragment() {
    private lateinit var binding: FragmentMiddleBinding
    private lateinit var mTodoViewModel: TodoViewModel
    private lateinit var mTodoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_middle, container, false)
        initRecyclerView(binding.recMiddle)
        initViewModel()
        mTodoAdapter.setOnClickListenser(object : TodoAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: TodoModel, pos: Int) {
                mTodoViewModel.updateTodoMiddleToAfter(data.id)
            }
        })



        return binding.root
    }

    private fun initViewModel() {
        mTodoViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            .create(TodoViewModel::class.java)
        mTodoViewModel.getTodoMiddleList().observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                mTodoAdapter.setTodoItems(it)
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                mTodoAdapter.setTodoItems(it)
                binding.tvEmpty.visibility = View.GONE
            }
        })
    }

    private fun initRecyclerView(rec: RecyclerView) {
        mTodoAdapter = TodoAdapter()
        rec.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mTodoAdapter
        }
    }

}