package com.example.todo_mvvm

open class Event<out T>(private val content: T) {

    var hasBennHandled = false
        private set // Allow external read but not write

    fun getContentIfNotHandled(): T? {
        return if (hasBennHandled) {
            null
        } else {
            hasBennHandled = true
            content
        }
    }

    fun peekContent(): T = content

}