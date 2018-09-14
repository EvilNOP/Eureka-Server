package io.github.eureka.web

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class UserHandler {

    private val users = Flux.just("Hello", "World")

    fun findAll(request: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().body(users)
}
