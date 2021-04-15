package com.triveous.todo.app.services

import com.triveous.todo.app.domain.User

import com.triveous.todo.app.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MyUserDetailService(private val repository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = repository.findUserByName(username!!)
        return org.springframework.security.core.userdetails.User(user?.username, user?.password, emptyList())
    }
}