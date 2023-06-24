package com.example.todo_mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.todo_mvvm.R
import com.example.todo_mvvm.databinding.FragmentCompleteBinding

class CompleteFragment: Fragment() {

    private lateinit var binding: FragmentCompleteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_complete, container, false)
        return binding.root
    }
}