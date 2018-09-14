package io.github.eureka

import io.github.eureka.bean.beans
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootConfiguration
@EnableAutoConfiguration
class Eureka

fun main(args: Array<String>) {
    runApplication<Eureka>(*args) {
        addInitializers(beans())
    }
}
