package ru.netology.diplom_weather.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrentDto(
    val temp_c: Double,
    val condition: ConditionDto,
)