package com.webfluxdemo.routehandlers

import data.people
import models.Person
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

// Fetches one user from the database
fun fetchPersonById(request: ServerRequest): Mono<ServerResponse> {
  val personId = request.pathVariable("id")

  // *** Mock DB fetch *** //
  val person = people.find {
    it.id==personId.toInt()
  }
  // ********************* //

  return if (person==null) {
    ServerResponse
      .status(500)
      .syncBody("No person with the id $personId in the database\n")
  } else {
    ServerResponse
      .ok()
      .syncBody(person)
  }
}

fun fetchPeople(
  @Suppress("UNUSED_PARAMETER")
  request: ServerRequest
): Mono<ServerResponse> {
  return ServerResponse
    .ok()
    .syncBody(people)
}

fun addPerson(request: ServerRequest): Mono<ServerResponse> {
  val id = people.maxBy { it.id }!!.id + 1
  val newPerson = request.bodyToMono(Person::class.java).map {
    it.id = id
    // *** Mock DB Save *** //
    people.add(it)
    // ******************** //
    it
  }

  return ServerResponse
    .ok()
    .body(newPerson, Person::class.java)
}

fun updatePerson(request: ServerRequest): Mono<ServerResponse> {
  val id = request.pathVariable("id").toInt()
  val foundPerson = people.find {
    it.id==id
  }

  return if (foundPerson==null) {
    ServerResponse
      .status(500)
      .syncBody("No person found in DB with id $id")
  } else {
    val updatedPerson = request.bodyToMono(Person::class.java)
      .doOnSuccess { updatedPerson ->
        updatedPerson.id = id
        people = people.map { person ->
          if (person.id==id) {
            updatedPerson
          } else {
            person
          }
        }.toMutableList()
      }

    return ServerResponse
      .ok()
      .body(updatedPerson, Person::class.java)
  }
}
