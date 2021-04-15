package com.triveous.todo.app.services

import com.triveous.todo.app.domain.User
import com.triveous.todo.app.exception.EtAuthException
import com.triveous.todo.app.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.regex.Pattern


@Service
class UserService(private val repository: UserRepository)  {

  @Throws(EtAuthException::class)
  fun registerUser(firstName: String, lastName: String, email: String, password: String, username: String) : User? {

    val pattern: Pattern = Pattern.compile("^(.+)@(.+)$")
    email.toLowerCase()
    if (!pattern.matcher(email).matches()) throw EtAuthException("Invalid email format")
    val count: Int = repository.getCountByEmail(email)
    if (count > 0) throw EtAuthException("Email already in use")
    val userId: Int = repository.create(firstName, lastName, email, password,username)
    return repository.findById(userId)
  }

  @Throws(EtAuthException::class)
  fun validateUser(email: String, password: String?): User? {
    return repository.findByFirstNameAndPassword(firstName = email,password= password!!)
  }

  fun loadUserByEmail(email: String) : User?{
    return repository.findUserByName(email)
  }
}