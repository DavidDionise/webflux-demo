package com.webfluxdemo

import com.webfluxdemo.routehandlers.addPerson
import com.webfluxdemo.routehandlers.fetchPeople
import com.webfluxdemo.routehandlers.fetchPersonById
import com.webfluxdemo.routehandlers.updatePerson
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfig() {

    @Bean
    fun routes(): RouterFunction<ServerResponse> = router {
        // Fetch all people
        GET("/people", ::fetchPeople)
        // Fetch person
        GET("/people/{id}", ::fetchPersonById)
        // Add person
        POST("/people", ::addPerson)
//        // Update person
        PUT("/people/{id}", ::updatePerson)

        // Fetch weather for one city
        // Fetch weather for several cities
        // Fetch weather for one city and transform
        // Fetch weather for several cities and transform
    }
}


