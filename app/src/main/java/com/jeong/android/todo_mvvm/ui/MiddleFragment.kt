package com.jeong.android.todo_mvvm.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
                val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_middle, null)
                val alertDialog = AlertDialog.Builder(requireContext(), R.style.CustomDialog)
                    .setView(dialogView)
                    .create()

                val selectTodo = dialogView.findViewById<TextView>(R.id.dialog_tv_todo)
                val btnOk = dialogView.findViewById<Button>(R.id.dialog_btn_ok)
                val btnCancel = dialogView.findViewById<ImageView>(R.id.dialog_ic_cancel)

                selectTodo.text = data.description

                btnOk.setOnClickListener {
                    mTodoViewModel.updateTodoMiddleToAfter(data.id)
                    alertDialog.dismiss()
                }

                btnCancel.setOnClickListener {
                    alertDialog.dismiss()
                }

                alertDialog.show()
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