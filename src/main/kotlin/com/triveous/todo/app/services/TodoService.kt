package com.triveous.todo.app.services

import com.triveous.todo.app.exception.EtAuthException
import com.triveous.todo.app.repositories.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoService(private val repository: TodoRepository) {
    fun addTodo(name: String, reminder_time: Int, status: Int, priority: Int) {
        val count: Int = repository.getCountByName(name)
        if (count > 0) throw EtAuthException("Todo with Name -> $name already exists")
        if (status < 0 || status > 1) throw  EtAuthException("Invalid Status")
        if (priority < 0 || priority > 3) throw  EtAuthException("Priority can be from 0 to 3")
        return repository.createTodo(name = name, reminder_time = reminder_time, status = status, priority = priority)
    }

    fun getAllTodos() = repository.getAllTodos()
    fun deleteTodoWithId(id: Int) = repository.deleteTodoWithId(id)
    fun editToDo(id: Int, name: String?, reminder_time: Int?, status: Int?, priority: Int?) {
        val count: Int = repository.getCountByName(name!!)
        if (count > 0) throw EtAuthException("Todo with Name -> $name already exists")
        if (status!! < 0 || status > 1) throw  EtAuthException("Invalid Status")
        if (priority!! < 0 || priority > 3) throw  EtAuthException("Priority can be from 0 to 3")
        return repository.editTodo(name = name, reminder_time = reminder_time,
                status = status, priority = priority, id = id)
    }

    fun getCompletedTodos() = repository.getAllCompletedTodos()
    fun getIncompleteTodos() = repository.getIncompleteTodos()
    fun updateStatus(id: Int, status: Int) {
        if (status < 0 || status > 1) throw  EtAuthException("Invalid Status")
        return repository.updateStatus(status = status,id= id)
    }

    fun updatePriority(id: Int, priority: Int) {
        if (priority!! < 0 || priority > 3) throw  EtAuthException("Priority can be from 0 to 3")
        return repository.updatePriority(priority = priority,id= id)
    }

}