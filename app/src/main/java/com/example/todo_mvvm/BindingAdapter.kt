package com.example.todo_mvvm

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("dateString")
    fun dateString(view: TextView, date: String) {
        val dateFormat = date.substring(5)
        view.text = dateFormat
    }

    @JvmStatic
    @BindingAdapter("todoCheck")
    fun strikeThrough(view: TextView, check: Boolean) {
        if (check) {
            view.paintFlags = view.paintFlags.or(
                Paint.STRIKE_THRU_TEXT_FLAG
            )
        } else {
            view.paintFlags = view.paintFlags.and(
                Paint.STRIKE_THRU_TEXT_FLAG.inv()
            )
        }
    }
}
