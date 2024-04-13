package ru.netology.diplom_weather.data

import kotlinx.coroutines.flow.Flow
import ru.netology.diplom_weather.data.dto.WeatherDto

interface Repository {

    val weather: Flow<WeatherDto?>

    fun getWeatherData()
}