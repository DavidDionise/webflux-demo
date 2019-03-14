package com.webfluxdemo.routehandlers

import models.CityWeather
import models.WeatherApiResponse
import models.WeatherReport
import models.WeatherRequest
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URI

val API_KEY = "eb0cbe452a9030be412c4819637ce278"

val client = WebClient.create("https://api.openweathermap.org/data/2.5/weather")
fun getWeatherUrl(city: String): String {
  return "?q=$city&apiKey=$API_KEY"
}

//fun fetchWeather(request: ServerRequest): Mono<ServerResponse> {
//  return ServerResponse
//    .ok()
//    .contentType(MediaType.APPLICATION_JSON)
//    .body(
//      Mono.zip(
//        mutableListOf(
//          request.bodyToMono(WeatherRequest::class.java)
//            .map { weatherRequest: WeatherRequest ->
//              weatherRequest.cities.map {
//                client
//                  .get()
//                  .uri(URI.create(getWeatherUrl(it)))
//                  .retrieve()
//                  .bodyToMono(WeatherApiResponse::class.java)
//              }
//            }
//        )
//      ) { weatherResponses: List<WeatherApiResponse> ->
//        WeatherReport(
//          weatherResponses.map {
//            CityWeather(
//              it.name,
//              it.main.temp
//            )
//          }
//        )
//      }
//    )
//}
