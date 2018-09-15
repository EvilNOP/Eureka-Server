package io.github.eureka.bean

import io.github.eureka.web.Routes
import io.github.eureka.web.UserHandler
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.RouterFunctions

fun beans() = beans {

    bean<UserHandler>()
    bean<Routes>()
    bean("webHandler") {
        RouterFunctions.toWebHandler(ref<Routes>().router())
    }
}
