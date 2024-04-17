package ru.netology.diplom_weather.data.dto

import kotlinx.serialization.Serializable
import ru.netology.diplom_weather.R

@Serializable
data class AstroDto(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moon_phase: String,
    val moon_illumination: Int,
){

    val valueToDescription: Map<String, Int>
        get() = mapOf(
            sunrise to R.string.sunrise,
            sunset to R.string.sunset,
            moonrise to R.string.moonrise,
            moonset to R.string.moonset,
            moon_phase to R.string.moon_phase,
            moon_illumination.toString() to R.string.moon_illumination,
        )
}