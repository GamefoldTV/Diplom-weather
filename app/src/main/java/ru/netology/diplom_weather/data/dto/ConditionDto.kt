package ru.netology.diplom_weather.data.dto

import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat

@Serializable
data class ConditionDto(
    val code: Int,
    val icon: String,
    val text: String
)


