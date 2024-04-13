package ru.netology.diplom_weather.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ForecastDto(
    val forecastday: List<ForecastDayDto>
)