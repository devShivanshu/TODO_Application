package com.triveous.todo.app.resources

import com.triveous.todo.app.Constants
import com.triveous.todo.app.domain.User
import com.triveous.todo.app.exception.EtAuthException
import com.triveous.todo.app.services.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import java.util.*


@RestController
@RequestMapping("/api/users")
class UserResource(@Autowired private val service: UserService, @Autowired private val authenticationManager: AuthenticationManager) {



    @PostMapping("/register")
    public fun registerUser(@RequestBody userMap: Map<String, Any>): ResponseEntity<Map<String, String>> {
        val firstName = userMap["firstName"] as String
        val lastName = userMap["lastName"] as String
        val email = userMap["email"] as String
        val password = userMap["password"] as String
        val username = userMap["username"] as String
        val user: User? = service.registerUser(firstName = firstName, lastName = lastName, email = email, password = password, username= username)
        return ResponseEntity(generateJWTToken(user), HttpStatus.OK)
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody userMap: Map<String?, Any?>): ResponseEntity<Map<String, String>> {
        val email = userMap["username"] as String
        val password = userMap["password"] as String?
        val user: User? = service.validateUser(email, password)
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        } catch (e: Exception) {
            throw  EtAuthException(message = e.message!!)
        }
        val user1 = service.loadUserByEmail(email);
        val userDetails : UserDetails = org.springframework.security.core.userdetails.User(user1?.email,user1?.password, emptyList())
        return ResponseEntity(generateJWTToken(user), HttpStatus.OK)
    }

    private fun generateJWTToken(user: User?): Map<String, String>? {
        val timestamp = System.currentTimeMillis()
        val token: String = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(Date(timestamp))
                .setExpiration(Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userId", user?.userId)
                .claim("email", user?.email)
                .claim("firstName", user?.firstName)
                .claim("lastName", user?.lastName)
                .claim("username",user?.username)
                .compact()
        val map: MutableMap<String, String> = HashMap()
        map["token"] = token
        return map
    }



}