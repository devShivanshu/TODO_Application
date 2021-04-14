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
        const val SQL_CREATE = "INSERT INTO TRVS_TODO(ID,NAME,REMINDER_TIME,STATUS,PRIORITY) values (NEXTVAL('TRVS_TODO_SEQ'),?,?,?,?)"
        const val SQL_GET_ALL_TODOS = "SELECT * FROM TRVS_TODO"
        const val SQL_DELETE_TODO_WITH_ID = "DELETE FROM TRVS_TODO WHERE ID = ?"
        const val SQL_UPDATE_TODO = "UPDATE TRVS_TODO SET NAME= ?, REMINDER_TIME= ?,STATUS= ?, PRIORITY = ? WHERE ID= ?"
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

    override fun getAllTodos(): Collection<Todo> {
       return jdbcTemplate.query(SQL_GET_ALL_TODOS,todoRowMapper)
    }

    override fun deleteTodoWithId(id: Int): Int{
        return jdbcTemplate.update(SQL_DELETE_TODO_WITH_ID, id)
    }

    override fun editTodo(name: String?, status: Int?, priority: Int?, reminder_time: Int?,id: Int) {
        try{
            jdbcTemplate.update(SQL_UPDATE_TODO, name,reminder_time ,status,priority,id)
        } catch (e: Exception){
          throw  EtAuthException("COULD NOT UPDATE DUE TO ${e.message}")
        }
    }

    private val todoRowMapper: RowMapper<Todo> = RowMapper<Todo> { rs, rowNum ->
        Todo(rs.getInt("ID"),
                rs.getString("NAME"),
                rs.getString("REMINDER_TIME"),
                rs.getInt("STATUS"),
                rs.getInt("PRIORITY"))
    }


}