package com.triveous.todo.app.services

import com.triveous.todo.app.domain.User
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//import com.triveous.todo.app.repositories.UserRepository
//import java.util.regex.Pattern
//
//@Service
//@Transactional
//class UserServiceImpl(private val userRepository: UserRepository) : UserService {
//
////    override fun validateUser(email: String, password: String): User {
////        TODO("Not yet implemented")
////
////    }
//
//    override fun registerUser(firstName: String, lastName: String, email: String, password: String): User? {
//        val pattern: Pattern = Pattern.compile("ˆ(.+)@(.+)$")
//        print("HERE USER SERVICE IMPL Registering User$firstName :: $lastName::  $password::$email")
//        val userID : Int = userRepository.create(firstName,lastName,email,password)
//        print("HERE" + "Registering User 2")
//        return userRepository.findById(userID);
//    }
//}