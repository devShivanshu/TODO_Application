package com.triveous.todo.app.repositories

import com.triveous.todo.app.domain.User

interface UserRepository {

    fun create(firstName: String, lastName: String, email:String, password: String, username: String) : Int
    fun findByEmailAndPassword(email: String,password: String?) : User?
    fun getCountByEmail(email: String) : Int
    fun findById(userId: Int): User?
    fun findUserByName(name:String) : User?
    fun findByFirstNameAndPassword(firstName: String,password: String): User?

}