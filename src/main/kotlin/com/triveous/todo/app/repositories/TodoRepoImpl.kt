package com.triveous.todo.app.repositories

import com.triveous.todo.app.domain.Todo
import com.triveous.todo.app.exception.EtAuthException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.Statement
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Repository
class TodoRepoImpl(@Autowired private val jdbcTemplate: JdbcTemplate) : TodoRepository {

    companion object {
        const val SQL_CREATE = "INSERT INTO TRVS_TODO(ID,NAME,REMINDER_TIME,STATUS,PRIORITY,USER_ID) values (NEXTVAL('TRVS_TODO_SEQ'),?,?,?,?,?)"
        const val SQL_GET_ALL_TODOS = "SELECT * FROM TRVS_TODO WHERE user_id = ? ORDER BY PRIORITY"
        const val SQL_DELETE_TODO_WITH_ID = "DELETE FROM TRVS_TODO WHERE (ID = ? AND USER_ID = ?)"
        const val SQL_UPDATE_TODO = "UPDATE TRVS_TODO SET NAME= ?, REMINDER_TIME= ?,STATUS= ?, PRIORITY = ? WHERE ID= ? AND USER_ID = ?"
        const val SQL_COUNT_BY_NAME = "SELECT COUNT(*) FROM TRVS_TODO WHERE (NAME = ? AND USER_ID =?)"
        const val SQL_GET_COMPLETED_TODOS = "SELECT * FROM TRVS_TODO WHERE (STATUS = 1 AND  USER_ID = ?)"
        const val SQL_GET_INCOMPLETE_TODOS = "SELECT * FROM TRVS_TODO WHERE (STATUS = 0 AND  USER_ID = ?)"
        const val SQL_UPDATE_STATUS = "UPDATE TRVS_TODO SET STATUS = ? WHERE (ID = ? AND  USER_ID = ?)"
        const val SQL_UPDATE_PRIORITY = "UPDATE TRVS_TODO SET PRIORITY = ? WHERE ( ID = ? AND  USER_ID = ?)"
    }

    override fun createTodo(name: String, reminder_time: Int, status: Int, priority: Int,user_id: Int) {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        try {
            jdbcTemplate.update({
                val ps: PreparedStatement = it.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)
                ps.setString(1, name)
                ps.setInt(2, reminder_time)
                ps.setInt(3, status)
                ps.setInt(4, priority)
                ps.setInt(5,user_id)
                return@update ps
            }, keyHolder)
        } catch (e: Exception) {
            throw  EtAuthException("Invalid Details, Failed to create account  ${e.message}")
        }

    }

    override fun getAllTodos(user_id: Int): Collection<Todo> {
        return jdbcTemplate.query(SQL_GET_ALL_TODOS, arrayOf<Any>(user_id), todoRowMapper)
    }

    override fun deleteTodoWithId(id: Int,user_id: Int): Int {
        return jdbcTemplate.update(SQL_DELETE_TODO_WITH_ID, id,user_id)
    }

    override fun editTodo(name: String?, status: Int?, priority: Int?, reminder_time: Int?, id: Int,user_id: Int) {
        try {
            jdbcTemplate.update(SQL_UPDATE_TODO, name, reminder_time, status, priority, id,user_id)
        } catch (e: Exception) {
            throw  EtAuthException("COULD NOT UPDATE DUE TO ${e.message}")
        }
    }

    override fun getCountByName(name: String,user_id: Int): Int {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_NAME, arrayOf<Any>(name,user_id), Int::class.java)
    }

    override fun getAllCompletedTodos(user_id: Int): Collection<Todo> {
        return jdbcTemplate.query(SQL_GET_COMPLETED_TODOS,arrayOf<Any>(user_id), todoRowMapper)
    }

    override fun getIncompleteTodos(user_id: Int): Collection<Todo> {
        return jdbcTemplate.query(SQL_GET_INCOMPLETE_TODOS,arrayOf<Any>(user_id), todoRowMapper)
    }

    override fun updateStatus(status: Int?, id: Int,user_id: Int) {
        try {
            jdbcTemplate.update(SQL_UPDATE_STATUS, status, id,user_id)
        } catch (e: java.lang.Exception) {
            throw  EtAuthException("Could Not update due to ${e.message}")
        }
    }

    override fun updatePriority(priority: Int?, id: Int,user_id: Int) {
        try {
            jdbcTemplate.update(SQL_UPDATE_PRIORITY, priority, id,user_id)
        } catch (e: java.lang.Exception) {
            throw  EtAuthException("Could Not update due to ${e.message}")
        }
    }

    private val todoRowMapper: RowMapper<Todo> = RowMapper<Todo> { rs, rowNum ->
        Todo(rs.getInt("ID"),
                rs.getString("NAME"),
                rs.getString("REMINDER_TIME"),
                rs.getInt("STATUS"),
                rs.getInt("PRIORITY"),
                rs.getInt("USER_ID")
                )
    }
}