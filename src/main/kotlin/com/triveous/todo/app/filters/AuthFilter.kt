package com.triveous.todo.app.filters

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.triveous.todo.app.Constants


class AuthFilter : GenericFilterBean() {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = request as HttpServletResponse

        val authHeader = httpRequest.getHeader("Authorization")
        if (authHeader != null) {
            val authHeaderArr: Array<String?> = authHeader.split("Bearer ").toTypedArray()
            if (authHeaderArr.size > 1 && authHeaderArr[1] != null) {
                val token = authHeaderArr[1]
                try {
                    val claims: Claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY)
                            .parseClaimsJws(token).getBody()
                    httpRequest.setAttribute("userId", claims["userId"].toString().toInt())
                } catch (e: Exception) {
                    httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid/expired token")
                    return
                }
            } else {
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be Bearer [token]")
                return
            }
        } else {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided")
            return
        }
        chain?.doFilter(request, response)
    }

}