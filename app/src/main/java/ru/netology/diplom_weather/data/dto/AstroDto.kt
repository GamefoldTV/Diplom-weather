package ru.netology.diplom_weather.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AstroDto(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moon_phase: String,
    val moon_illumination: Int,
)