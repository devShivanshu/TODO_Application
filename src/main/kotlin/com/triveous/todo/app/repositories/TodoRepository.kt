package com.triveous.todo.app.repositories

import com.triveous.todo.app.domain.Todo

interface TodoRepository {
    fun createTodo(name: String,reminder_time: Int,status: Int,priority: Int,user_id: Int)
    fun getAllTodos(user_id: Int)  : Collection<Todo>
    fun deleteTodoWithId(id: Int,user_id: Int) : Int
    fun editTodo(name: String?,status: Int?,priority: Int?,reminder_time: Int?, id: Int,user_id: Int)
    fun getCountByName(name: String,user_id: Int) : Int
    fun getAllCompletedTodos(user_id: Int) : Collection<Todo>
    fun getIncompleteTodos(user_id: Int) : Collection<Todo>
    fun updateStatus(status: Int?, id: Int,user_id: Int)
    fun updatePriority(priority: Int?,id: Int,user_id: Int)
}