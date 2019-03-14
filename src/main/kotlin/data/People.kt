package data

import models.Person

// Serves as a mock DB for the 'Person' route handlers
var people = mutableListOf(
    Person(
        1,
        "Lydia",
        32
    ),
    Person(
        2,
        "John",
        33
    ),
    Person(
        3,
        "Henri",
        12
    ),
    Person(
        4,
        "Amanda",
        45
    )
)
