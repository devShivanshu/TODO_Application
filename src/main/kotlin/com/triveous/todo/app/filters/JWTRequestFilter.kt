package com.triveous.todo.app.filters

import com.triveous.todo.app.Util.JWTUtility
import com.triveous.todo.app.services.MyUserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JWTRequestFilter(@Autowired private val userDetailService: MyUserDetailService, @Autowired private val jwtUtility: JWTUtility) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")
        lateinit var username : String
        lateinit var token :String

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7)
            username = jwtUtility.extractUsername(token)
        }
        if(SecurityContextHolder.getContext().authentication == null){
            print("REQUEST URL  ${request.requestURL}")
            if(request.requestURL.contains("login") || request.requestURL.contains("register")){
//                var requestBody = request.reader.lines().collect(Collectors.joining())
//                requestBody = """$requestBody""".trimIndent()
//                val gson = Gson()
//                var user = gson.fromJson(requestBody,RequestUser::class.java)
//                print("USERNAME = ${user.username}")
            }
            else {
                val userDetails : UserDetails = this.userDetailService.loadUserByUsername(username)

                if (jwtUtility.validateToken(token, userDetails)!!) {
                    val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.authorities)
                    usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                }
            }


        }
       filterChain.doFilter(request, response)
    }
}
