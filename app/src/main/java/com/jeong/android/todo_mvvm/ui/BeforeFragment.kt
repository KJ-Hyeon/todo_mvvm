package com.jeong.android.todo_mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jeong.android.todo_mvvm.common.BaseFragment
import com.jeong.android.todo_mvvm.databinding.FragmentBeforeBinding
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
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}