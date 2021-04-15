package com.triveous.todo.app.resources

import com.triveous.todo.app.domain.Todo
import com.triveous.todo.app.services.TodoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/todo")
class TodoResource(@Autowired private val service: TodoService) {

    @PostMapping("/add")
    public fun addTodo(@RequestBody todoMap: Map<String, Any>,httpServletRequest: HttpServletRequest): ResponseEntity<Map<String, String>> {
        val name = todoMap["name"] as String
        val reminderTime = todoMap["reminder_time"] as Int
        val status = todoMap["status"] as Int
        val priority = todoMap["priority"] as Int

        print("HTTP SERVLET REQUEST ${httpServletRequest.getHeader("Authorization")}")

        service.addTodo(name = name, reminder_time = reminderTime, status = status, priority = priority)
        val map: MutableMap<String, String> = HashMap()
        map.put("message", "Successfully added Todo")
        return ResponseEntity(map, HttpStatus.OK)
    }

    @GetMapping("/getTodos")
    public fun getAllTodos(): Collection<Todo> {
        return service.getAllTodos()
    }

    @GetMapping("/completed")
    public fun getCompletedTodos(): Collection<Todo> {
        return service.getCompletedTodos()
    }

    @GetMapping("/incomplete")
    public fun getIncompleteTodos(): Collection<Todo> {
        return service.getIncompleteTodos()
    }

    @DeleteMapping("/deleteWithId")
    public fun deleteTodoWithId(id: Int): Int = service.deleteTodoWithId(id = id)

    @PutMapping("/update")
    public fun updateTodo(@RequestBody todoMap: Map<String, Any>, id: Int): ResponseEntity<Map<String, String>> {
        val name = todoMap["name"] as String
        val reminderTime = todoMap["reminder_time"] as Int
        val status = todoMap["status"] as Int
        val priority = todoMap["priority"] as Int

        service.editToDo(id = id, name = name, reminder_time = reminderTime, status = status, priority = priority)
        val map: MutableMap<String, String> = HashMap()
        map.put("message", "Successfully Updated Todo")
        return ResponseEntity(map, HttpStatus.OK)
    }

    @PutMapping("/updateStatus")
    public fun updateStatus(status: Int, id: Int) {
        service.updateStatus(id = id, status = status)
    }

    @PutMapping("/updatePriority")
    public fun updatePriority(priority: Int, id: Int) {
        service.updatePriority(id = id, priority = priority)
    }


}