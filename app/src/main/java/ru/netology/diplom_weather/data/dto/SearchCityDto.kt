package ru.netology.diplom_weather.data.dto


data class SearchCityDto(
    val id: Long,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val url: String,
)