package ru.netology.diplom_weather.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ForecastDayDto(
    val astro: AstroDto,
    val date: String, // "2024-04-11"
    val date_epoch: Int,
    val day: DayDto,
    val hour: List<HourDto>
)