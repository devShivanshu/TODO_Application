package com.triveous.todo.app.resources

import com.triveous.todo.app.Util.JWTUtility
import com.triveous.todo.app.domain.Todo
import com.triveous.todo.app.services.TodoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import kotlin.properties.Delegates

@RestController
@RequestMapping("/api/todo")
class TodoResource(@Autowired private val service: TodoService, @Autowired private val jwtUtility: JWTUtility) {

    @PostMapping("/add")
    public fun addTodo(@RequestBody todoMap: Map<String, Any>,httpServletRequest: HttpServletRequest): ResponseEntity<Map<String, String>> {
        val name = todoMap["name"] as String
        val reminderTime = todoMap["reminder_time"] as Int
        val status = todoMap["status"] as Int
        val priority = todoMap["priority"] as Int
        val authorizationHeader = httpServletRequest.getHeader("Authorization")
        var userId by Delegates.notNull<Int>()
        lateinit var token :String

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7)
            userId = jwtUtility.extractUserId(token)
        }
        print("User Id $userId")

        service.addTodo(name = name, reminder_time = reminderTime, status = status, priority = priority,user_id = userId)
        val map: MutableMap<String, String> = HashMap()
        map["message"] = "Successfully added Todo"
        return ResponseEntity(map, HttpStatus.OK)
    }

    @GetMapping("/getTodos")
    public fun getAllTodos(httpServletRequest: HttpServletRequest): Collection<Todo> {
        val authorizationHeader = httpServletRequest.getHeader("Authorization")
        var userId by Delegates.notNull<Int>()
        lateinit var token :String

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7)
            userId = jwtUtility.extractUserId(token)
        }
        return service.getAllTodos(user_id = userId)
    }

    @GetMapping("/completed")
    public fun getCompletedTodos(httpServletRequest: HttpServletRequest): Collection<Todo> {
        val authorizationHeader = httpServletRequest.getHeader("Authorization")
        var userId by Delegates.notNull<Int>()
        lateinit var token :String

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7)
            userId = jwtUtility.extractUserId(token)
        }
        return service.getCompletedTodos(user_id= userId)
    }

    @GetMapping("/incomplete")
    public fun getIncompleteTodos(httpServletRequest: HttpServletRequest): Collection<Todo> {
        val authorizationHeader = httpServletRequest.getHeader("Authorization")
        var userId by Delegates.notNull<Int>()
        lateinit var token :String

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7)
            userId = jwtUtility.extractUserId(token)
        }
        return service.getIncompleteTodos(user_id = userId)
    }

    @DeleteMapping("/deleteWithId")
    public fun deleteTodoWithId(id: Int,httpServletRequest: HttpServletRequest): Int  {
        val authorizationHeader = httpServletRequest.getHeader("Authorization")
        var userId by Delegates.notNull<Int>()
        lateinit var token :String

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7)
            userId = jwtUtility.extractUserId(token)
        }
        return service.deleteTodoWithId(id= id,user_id = userId)
    }

    @PutMapping("/update")
    public fun updateTodo(@RequestBody todoMap: Map<String, Any>, id: Int,httpServletRequest: HttpServletRequest): ResponseEntity<Map<String, String>> {
        val name = todoMap["name"] as String
        val reminderTime = todoMap["reminder_time"] as Int
        val status = todoMap["status"] as Int
        val priority = todoMap["priority"] as Int
        val authorizationHeader = httpServletRequest.getHeader("Authorization")
        var userId by Delegates.notNull<Int>()
        lateinit var token :String

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7)
            userId = jwtUtility.extractUserId(token)
        }
        service.editToDo(id = id, name = name, reminder_time = reminderTime, status = status, priority = priority,user_id = userId)
        val map: MutableMap<String, String> = HashMap()
        map.put("message", "Successfully Updated Todo")
        return ResponseEntity(map, HttpStatus.OK)
    }

    @PutMapping("/updateStatus")
    public fun updateStatus(status: Int, id: Int,httpServletRequest: HttpServletRequest) {
        val authorizationHeader = httpServletRequest.getHeader("Authorization")
        var userId by Delegates.notNull<Int>()
        lateinit var token :String

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7)
            userId = jwtUtility.extractUserId(token)
        }
        service.updateStatus(id = id, status = status,user_id= userId)
    }

    @PutMapping("/updatePriority")
    public fun updatePriority(priority: Int, id: Int,httpServletRequest: HttpServletRequest) {
        val authorizationHeader = httpServletRequest.getHeader("Authorization")
        var userId by Delegates.notNull<Int>()
        lateinit var token :String

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7)
            userId = jwtUtility.extractUserId(token)
        }
        service.updatePriority(id = id, priority = priority,user_id = userId)
    }


}