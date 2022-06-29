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
import com.jeong.android.todo_mvvm.databinding.FragmentAfterBinding
import com.jeong.android.todo_mvvm.model.TodoModel
import com.jeong.android.todo_mvvm.viewmodel.TodoViewModel

class AfterFragment : BaseFragment() {
    private lateinit var binding: FragmentAfterBinding
    private lateinit var mTodoViewModel: TodoViewModel
//    private lateinit var mTodoAdapter: TodoAdapter
    private lateinit var mTodoAfterAdapter: TodoAfterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_after, container, false)
        initRecyclerView(binding.recAfter)
        initViewModel()

//        mTodoAfterAdapter.setOnClickListenser(object : TodoAdapter.OnItemClickListener{
//            override fun onItemClick(v: View, data: TodoModel, pos: Int) {
//                mTodoViewModel.deleteTodo(data)
//            }
//        })
        mTodoAfterAdapter.setOnItemClick(object : TodoAfterAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: TodoModel, pos: Int) {
                mTodoViewModel.deleteTodo(data)
            }
        })

        return binding.root
    }
    private fun initViewModel() {
        mTodoViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            .create(TodoViewModel::class.java)
        mTodoViewModel.getTodoAfterList().observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                mTodoAfterAdapter.setAfterItems(it)
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                mTodoAfterAdapter.setAfterItems(it)
                binding.tvEmpty.visibility = View.GONE
            }
        })
    }

    private fun initRecyclerView(rec: RecyclerView) {
        mTodoAfterAdapter = TodoAfterAdapter()
        rec.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mTodoAfterAdapter
        }
    }

}