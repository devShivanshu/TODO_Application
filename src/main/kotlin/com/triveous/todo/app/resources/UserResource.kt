package com.triveous.todo.app.resources

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserResource {

    @PostMapping("/register")
    public fun registerUser(@RequestBody userMap: Map<String, Any>): String {
        val firstName: Any? = userMap.get("firstName")
        val lastName : Any? = userMap.get("lastName")
        val email : Any? = userMap.get("email")
        val password : Any? = userMap.get("password");
        return "Name ::  $firstName  , $lastName , Email ::  $email , $password "
    }


}