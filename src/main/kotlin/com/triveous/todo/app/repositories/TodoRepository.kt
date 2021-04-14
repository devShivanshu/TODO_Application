package com.triveous.todo.app.repositories

interface TodoRepository {
    fun createTodo(name: String,reminder_time: Int,status: Int,priority: Int)
}