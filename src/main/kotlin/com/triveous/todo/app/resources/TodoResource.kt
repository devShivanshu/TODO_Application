package com.triveous.todo.app.resources

import com.triveous.todo.app.services.TodoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/todo")
class TodoResource(@Autowired private val service: TodoService) {

    @PostMapping("/add")
    public fun addTodo(@RequestBody todoMap: Map<String,Any>): ResponseEntity<Map<String,String>>{
        val name = todoMap["name"] as String
        val reminderTime = todoMap["reminder_time"] as Int
        val status = todoMap["status"] as Int
        val priority = todoMap["priority"] as Int

        service.addTodo(name = name,reminder_time = reminderTime,status = status,priority= priority)
        val map : MutableMap<String,String> = HashMap()
        map.put("message","Successfully added Todo")
        return ResponseEntity(map,HttpStatus.OK)
    }
}