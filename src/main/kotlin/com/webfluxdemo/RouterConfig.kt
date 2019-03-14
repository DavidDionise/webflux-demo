package com.webfluxdemo

import com.webfluxdemo.routehandlers.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
@Suppress("UNUSED") // Used by spring
class RouterConfig(
  val weatherHandler: WeatherHandler
) {

  @Bean
  @Suppress("UNUSED") // Used by spring
  fun routes(): RouterFunction<ServerResponse> = router {
    // Fetch all people
    GET("/people", ::fetchPeople)
    // Fetch person
    GET("/people/{id}", ::fetchPersonById)
    // Add person
    //
    // HTTP body example: {
    //    name: "Lola",
    //    age: 33
    // }
    POST("/people", ::addPerson)
    // Update person
    //
    // Same HTTP body data type as 'PUT /person{id}'
    PUT("/people/{id}", ::updatePerson)

    // Fetch weather for one city
    GET("/weather/{city}", weatherHandler::fetchWeatherByCity)
    // Fetch weather for several cities
    //
    // Takes { cities: [ <city-names> ] } in HTTP body
    // eg: {
    //    cities: [
    //      "detroit",
    //      "chicago"
    //    ]
    // }
    POST("/weather", weatherHandler::fetchWeatherByCities)
  }
}


