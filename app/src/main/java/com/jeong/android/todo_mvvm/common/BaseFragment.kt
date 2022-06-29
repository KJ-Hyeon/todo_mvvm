package com.jeong.android.todo_mvvm.common

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat

abstract class BaseFragment: Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    fun showMessage(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }
    fun currentDate(): String {
        val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(currentTime)
    }
}
// 날짜 구하는 함수