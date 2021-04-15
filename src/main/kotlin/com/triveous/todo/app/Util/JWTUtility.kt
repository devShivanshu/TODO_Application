package com.triveous.todo.app.Util

import com.triveous.todo.app.Constants
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function


@Service
class JWTUtility {
    fun extractUsername(token: String?): String {
        val claims = extractAllClaims(token)
        return claims["username"] as String
    }

    fun extractExpiration(token: String?): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        val t =  claimsResolver.apply(claims)
        return  t
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts.parser().setSigningKey(Constants.API_SECRET_KEY).parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String?): Boolean? {
        return extractExpiration(token).before(Date())
    }


    fun validateToken(token: String?, userDetails: UserDetails): Boolean? {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)!!
    }
}