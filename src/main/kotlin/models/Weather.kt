package models

data class WeatherReport(
    val cities: List<CityWeather>
)

data class CityWeather(
    val cityName: String,
    val temp: Int
)

data class WeatherApiResponse(
    val name: String,
    val main: Temp
)

data class Temp(val temp: Int)

data class WeatherRequest(
    val cities: List<String>
)

