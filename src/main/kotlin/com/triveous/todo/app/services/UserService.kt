package com.triveous.todo.app.services

import com.triveous.todo.app.domain.User
import com.triveous.todo.app.exception.EtAuthException
import com.triveous.todo.app.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository)  {

  fun registerUser(firstName: String, lastName: String, email: String, password: String) : Int? = repository.create(firstName, lastName, email, password)
}