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

    @GetMapping("/getTodos")
    public fun getAllTodos(request: HttpServletRequest) : Collection<Todo> {
        return service.getAllTodos()
    }

    @DeleteMapping("/deleteWithId")
    public fun deleteTodoWithId(id: Int) : Int = service.deleteTodoWithId(id = id)

    @PutMapping("/update")
    public fun updateTodo(@RequestBody todoMap: Map<String, Any>, id: Int) : ResponseEntity<Map<String,String>>{
        val name = todoMap["name"] as String
        val reminderTime = todoMap["reminder_time"] as Int
        val status = todoMap["status"] as Int
        val priority = todoMap["priority"] as Int

        service.editToDo(id = id,name= name,reminder_time = reminderTime,status = status, priority= priority)
        val map : MutableMap<String,String> = HashMap()
        map.put("message","Successfully Updated Todo")
        return  ResponseEntity(map,HttpStatus.OK)
    }


}