package com.triveous.todo.app.resources

import com.triveous.todo.app.domain.User
import com.triveous.todo.app.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/users")
class UserResource(@Autowired private  val service: UserService) {

    @PostMapping("/register")
    public fun registerUser(@RequestBody userMap: Map<String, Any>): ResponseEntity<Map<String,String>> {
        val firstName = userMap["firstName"] as String
        val lastName = userMap["lastName"] as String
        val email = userMap["email"] as String
        val password = userMap["password"] as String
        service.registerUser(firstName = firstName,lastName = lastName,email = email, password = password)
        val map: MutableMap<String, String> = HashMap()
        map.put("message","Registered Successfully")
        return  ResponseEntity(map, HttpStatus.OK)
    }


}