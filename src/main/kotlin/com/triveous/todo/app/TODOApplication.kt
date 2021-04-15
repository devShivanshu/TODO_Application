package com.triveous.todo.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class TODOApplication

fun main(args: Array<String>) {
	runApplication<TODOApplication>(*args)


}
