package com.triveous.todo.app

import com.triveous.todo.app.filters.AuthFilter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean


@SpringBootApplication
class TODOApplication

fun main(args: Array<String>) {
	runApplication<TODOApplication>(*args)

	@Bean
	fun filterRegistrationBean() : FilterRegistrationBean<AuthFilter> {
		val registrationBean = FilterRegistrationBean<AuthFilter>()
		val authFilter = AuthFilter()
		registrationBean.filter = authFilter
		registrationBean.addUrlPatterns("/api/*")
		return registrationBean
	}

}
