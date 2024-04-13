package ru.netology.diplom_weather.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    val location: LocationDto,
    val current: CurrentDto,
    val forecast: ForecastDto
)