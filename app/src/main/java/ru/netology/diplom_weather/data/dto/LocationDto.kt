package ru.netology.diplom_weather.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val name: String,
)