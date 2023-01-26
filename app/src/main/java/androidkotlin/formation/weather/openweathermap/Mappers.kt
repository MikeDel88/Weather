package androidkotlin.formation.weather.openweathermap

import androidkotlin.formation.weather.weather.Weather

fun mapOpenWeatherDataToWeather(weatherWrapper: WeatherWrapper): Weather {
    val weatherFirst = weatherWrapper.weather.first()
    return Weather(
        description = weatherFirst.description,
        temperature = weatherWrapper.main.temperature,
        humidity = weatherWrapper.main.humidity,
        pressure = weatherWrapper.main.pressure,
        iconUrl = "https://openweathermap.org/img/w/${weatherFirst.icon}.png"
    )
}