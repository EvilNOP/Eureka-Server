package io.github.eureka.web

import org.springframework.http.MediaType

class Routes(
    private val userHandler: UserHandler
) {

    fun router() = org.springframework.web.reactive.function.server.router {
        accept(MediaType.APPLICATION_JSON_UTF8).nest {
            GET("/users", userHandler::findAll)
        }
    }
}
