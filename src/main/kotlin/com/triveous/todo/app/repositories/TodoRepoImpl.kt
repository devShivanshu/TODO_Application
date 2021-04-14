package com.triveous.todo.app.repositories

import com.triveous.todo.app.exception.EtAuthException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.Statement

@Repository
class TodoRepoImpl(@Autowired private val jdbcTemplate: JdbcTemplate) : TodoRepository {

    companion object {
        const val SQL_CREATE = "INSERT INTO TRVS_TODO(ID,NAME,REMINDER_TIME,STATUS,PRIORITY) values (NEXTVAL('TRVS_TODO_SEQ'),?,?,?,?)";
    }

    override fun createTodo(name: String, reminder_time: Int, status: Int, priority: Int) {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        try {
            jdbcTemplate.update({
                val ps: PreparedStatement = it.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)
                ps.setString(1, name)
                ps.setInt(2, reminder_time)
                ps.setInt(3, status)
                ps.setInt(4, priority)
                return@update ps
            }, keyHolder)
        } catch (e: Exception) {
            throw  EtAuthException("Invalid Details, Failed to create account  ${e.message}")
        }

    }

}