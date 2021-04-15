package com.triveous.todo.app.repositories

import com.triveous.todo.app.domain.Todo

interface TodoRepository {
    fun createTodo(name: String,reminder_time: Int,status: Int,priority: Int)
    fun getAllTodos()  : Collection<Todo>
    fun deleteTodoWithId(id: Int) : Int
    fun editTodo(name: String?,status: Int?,priority: Int?,reminder_time: Int?, id: Int)
    fun getCountByName(name: String) : Int
    fun getAllCompletedTodos() : Collection<Todo>
    fun getIncompleteTodos() : Collection<Todo>
    fun updateStatus(status: Int?, id: Int)
    fun updatePriority(priority: Int?,id: Int)
}