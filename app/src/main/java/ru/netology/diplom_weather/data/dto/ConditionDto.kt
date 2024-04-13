package ru.netology.diplom_weather.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConditionDto(
    val code: Int,
    val icon: String,
    val text: String
)