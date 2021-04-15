package com.triveous.todo.app.services

import com.triveous.todo.app.exception.EtAuthException
import com.triveous.todo.app.repositories.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoService(private val repository: TodoRepository) {
    fun addTodo(name: String, reminder_time: Int, status: Int, priority: Int,user_id: Int) {
        val count: Int = repository.getCountByName(name,user_id = user_id)
        if (count > 0) throw EtAuthException("Todo with Name -> $name already exists")
        if (status < 0 || status > 1) throw  EtAuthException("Invalid Status")
        if (priority < 0 || priority > 3) throw  EtAuthException("Priority can be from 0 to 3")
        return repository.createTodo(name = name, reminder_time = reminder_time, status = status, priority = priority,user_id = user_id)
    }

    fun getAllTodos(user_id: Int) = repository.getAllTodos(user_id = user_id)
    fun deleteTodoWithId(id: Int,user_id: Int) = repository.deleteTodoWithId(id,user_id = user_id)
    fun editToDo(id: Int, name: String?, reminder_time: Int?, status: Int?, priority: Int?,user_id: Int) {
        val count: Int = repository.getCountByName(name!!,user_id = user_id)
        if (count > 0) throw EtAuthException("Todo with Name -> $name already exists")
        if (status!! < 0 || status > 1) throw  EtAuthException("Invalid Status")
        if (priority!! < 0 || priority > 3) throw  EtAuthException("Priority can be from 0 to 3")
        return repository.editTodo(name = name, reminder_time = reminder_time,
                status = status, priority = priority, id = id,user_id = user_id)
    }

    fun getCompletedTodos(user_id: Int) = repository.getAllCompletedTodos(user_id = user_id)
    fun getIncompleteTodos(user_id: Int) = repository.getIncompleteTodos(user_id = user_id)
    fun updateStatus(id: Int, status: Int,user_id: Int) {
        if (status < 0 || status > 1) throw  EtAuthException("Invalid Status")
        return repository.updateStatus(status = status,id= id,user_id = user_id)
    }

    fun updatePriority(id: Int, priority: Int,user_id: Int) {
        if (priority < 0 || priority > 3) throw  EtAuthException("Priority can be from 0 to 3")
        return repository.updatePriority(priority = priority,id= id,user_id = user_id )
    }

}