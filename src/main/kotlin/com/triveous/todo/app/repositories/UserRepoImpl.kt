package com.triveous.todo.app.repositories

import com.triveous.todo.app.domain.User
import com.triveous.todo.app.exception.EtAuthException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.Statement

@Repository
class UserRepoImpl(@Autowired private val jdbcTemplate: JdbcTemplate) : UserRepository {

    companion object {
        const val SQL_CREATE = "INSERT INTO TRVS_USER(USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,USERNAME) values(NEXTVAL('TRVS_USER_SEQ'),?,?,?,?,?)";
        const val SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM TRVS_USER WHERE EMAIL = ?";
        const val SQL_COUNT_BY_USERNAME = "SELECT COUNT(*) FROM TRVS_USER WHERE USERNAME = ?"
        const val SQLFIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, USERNAME " + "FROM TRVS_USER WHERE USER_ID =?"
        private const val SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, USERNAME " +
                "FROM TRVS_USER WHERE EMAIL = ?"
        private const val SQL_FIND_BY_FIRST_NAME = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD , USERNAME " +
                "FROM TRVS_USER WHERE USERNAME = ?"
        private const val SQL_FIND_USER_BY_NAME = "SELECT * FROM TRVS_USER WHERE USERNAME= ? LIMIT 1"
    }


    override fun create(firstName: String, lastName: String, email: String, password: String, username: String): Int {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        try {
            jdbcTemplate.update({
                val ps: PreparedStatement = it.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)
                ps.setString(1, firstName)
                ps.setString(2, lastName)
                ps.setString(3, email)
                ps.setString(4, password)
                ps.setString(5,username)
                return@update ps
            }, keyHolder)

        } catch (e: Exception) {
            throw  EtAuthException("Invalid Details, Failed to create account  ${e.message}")
        }
        return keyHolder.keys?.get("USER_ID") as Int
    }


    override fun findByEmailAndPassword(email: String, password: String?): User? {
        try{
            val user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, arrayOf<Any>(email), userRowMapper)
            if(!password.equals(user?.password))
                throw EtAuthException("Invalid Email/Password")
            return user
        } catch (e: Exception){
           throw EtAuthException("INVALID email/password")
        }
    }

    override fun getCountByEmail(email: String): Int {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, arrayOf<Any>(email), Int::class.java)
    }

    override fun findById(userId: Int): User? {
        return jdbcTemplate.queryForObject(SQLFIND_BY_ID, arrayOf<Any>(userId), userRowMapper);
    }

    override fun findUserByName(name: String) : User? {
        return jdbcTemplate.queryForObject(SQL_FIND_USER_BY_NAME, arrayOf<Any>(name),userRowMapper)
    }

    override fun findByFirstNameAndPassword(firstName: String, password: String): User? {
        try{
            val user = jdbcTemplate.queryForObject(SQL_FIND_BY_FIRST_NAME, arrayOf<Any>(firstName), userRowMapper)
            if(!password.equals(user?.password))
                throw EtAuthException("Invalid Email/Password ")
            return user
        } catch (e: Exception){
            throw EtAuthException("INVALID email/password ${e.message}")
        }
    }

    override fun getCountByUsername(username: String): Int {
        return  jdbcTemplate.queryForObject(SQL_COUNT_BY_USERNAME, arrayOf<Any>(username), Int::class.java)
    }

    private val userRowMapper: RowMapper<User> = RowMapper<User> { rs, rowNum ->
        User(rs.getInt("USER_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"),
                rs.getString("USERNAME")
                )
    }
}