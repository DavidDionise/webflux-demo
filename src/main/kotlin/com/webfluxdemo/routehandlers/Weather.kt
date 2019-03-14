package com.webfluxdemo.routehandlers

import models.CityWeather
import models.WeatherApiResponse
import models.WeatherReport
import models.WeatherRequest
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

val API_KEY = System.getenv("API_KEY")

@Component
class WeatherHandler {

  private val client = WebClient.create("https://api.openweathermap.org")

  private fun callWeatherApi(city: String): Mono<WeatherApiResponse> {
    return client
      .get()
      .uri { uriBuilder ->
        uriBuilder
          .path("/data/2.5/weather")
          .queryParam("q", city)
          .queryParam("apiKey", API_KEY)
          .build()
      }
      .retrieve()
      .bodyToMono(WeatherApiResponse::class.java)
  }

  fun fetchWeatherByCity(request: ServerRequest): Mono<ServerResponse> {
    val cityName = request.pathVariable("city")

    return ServerResponse
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(
        callWeatherApi(cityName).map {
          CityWeather(
            it.name,
            it.main.temp
          )
        },
        CityWeather::class.java
      )
  }

  fun fetchWeatherByCities(request: ServerRequest): Mono<ServerResponse> {
    return ServerResponse
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(
        request.bodyToMono(WeatherRequest::class.java)
          .flatMap { weatherRequest: WeatherRequest ->
            Mono.zip(
              weatherRequest.cities.map { callWeatherApi(it) }
            ) { weatherApiResponses ->
              WeatherReport(
                weatherApiResponses.map {
                  val response = it as WeatherApiResponse
                  CityWeather(
                    response.name,
                    response.main.temp
                  )
                }
              )
            }
          } as Mono<WeatherReport>,
        WeatherReport::class.java
      )
  }
}
