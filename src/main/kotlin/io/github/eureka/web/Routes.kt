package io.github.eureka.web

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

class Routes(
    private val userHandler: UserHandler
) {

    fun router() = router {
        accept(MediaType.APPLICATION_JSON_UTF8).nest {
            GET("/users", userHandler::findAll)
        }
    }
}
