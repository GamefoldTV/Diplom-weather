package ru.netology.diplom_weather.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DayDto(
    val maxtemp_c: Double,
    val avgtemp_c: Double,
    val avgvis_km: Double,
    val condition: ConditionDto,
    val daily_chance_of_rain: Int,
    val daily_chance_of_snow: Int,
    val maxwind_kph: Double,
    val mintemp_c: Double,
    val totalprecip_mm: Double,
)