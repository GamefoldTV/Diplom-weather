package ru.netology.diplom_weather.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class HourDto(
    val condition: ConditionDto,
    val temp_c: Double,
    val time: String,
)