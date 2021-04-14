package com.triveous.todo.app.services

import com.triveous.todo.app.repositories.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoService(private val repository: TodoRepository) {
    fun addTodo(name: String, reminder_time: Int, status: Int, priority: Int) = repository.createTodo(name = name, reminder_time = reminder_time, status = status, priority = priority)
}