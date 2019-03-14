package com.webfluxdemo

import com.webfluxdemo.routehandlers.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfig(
  val weatherHandler: WeatherHandler
) {

  @Bean
  fun routes(): RouterFunction<ServerResponse> = router {
    // Fetch all people
    GET("/people", ::fetchPeople)
    // Fetch person
    GET("/people/{id}", ::fetchPersonById)
    // Add person
    POST("/people", ::addPerson)
    // Update person
    PUT("/people/{id}", ::updatePerson)

    // Fetch weather for one city
    GET("/weather/{city}", weatherHandler::fetchWeatherByCity)
    // Fetch weather for several cities
    POST("/weather", weatherHandler::fetchWeatherByCities)
  }
}


