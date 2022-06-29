package com.jeong.android.todo_mvvm.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeong.android.todo_mvvm.R
import com.jeong.android.todo_mvvm.common.BaseFragment
import com.jeong.android.todo_mvvm.databinding.FragmentBeforeBinding
import com.jeong.android.todo_mvvm.model.TodoModel
import com.jeong.android.todo_mvvm.viewmodel.TodoViewModel

class BeforeFragment : BaseFragment() {
    private lateinit var binding : FragmentBeforeBinding
    private lateinit var mTodoViewModel: TodoViewModel
    private lateinit var mTodoAdapter: TodoAdapter
    private lateinit var todoText: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_before, container, false)
        initRecyclerView(binding.recBefore)
        initViewModel()
        binding.todoViewModel = mTodoViewModel
        binding.lifecycleOwner = this

        mTodoViewModel.addBtnClickEvent.observe(viewLifecycleOwner, Observer {
            todoText = mTodoViewModel.text.value.toString()
            if (todoText.isNullOrEmpty()) {
                showMessage("할 일을 입력해주세요")

            } else {
                mTodoViewModel.insertTodo(TodoModel(null, todoText, "BEFORE"))
                Log.d("TAG", "onCreateView:${todoText}")
                mTodoViewModel.deleteText()
                HideEditTextKeyBoard()
            }
        })

        mTodoAdapter.setOnClickListenser(object : TodoAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: TodoModel, pos: Int) {
                mTodoViewModel.updateTodoBeforeToMiddle(data.id)
            }
        })

        return binding.root
    }

    private fun initViewModel() {
        mTodoViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            .create(TodoViewModel::class.java)
        mTodoViewModel.getTodoBeforeList().observe(viewLifecycleOwner, Observer {
            mTodoAdapter.setTodoItems(it)
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

    private fun HideEditTextKeyBoard() {
        val imm =
            this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.tvTodoBefore.windowToken, 0)
    }
}