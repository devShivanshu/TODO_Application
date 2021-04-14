package com.triveous.todo.app.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class EtAuthException : RuntimeException {
    constructor(message: String): super(message)
}