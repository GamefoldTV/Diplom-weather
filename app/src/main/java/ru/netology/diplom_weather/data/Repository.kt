package ru.netology.diplom_weather.data

import kotlinx.coroutines.flow.Flow
import ru.netology.diplom_weather.data.dto.SearchCityDto
import ru.netology.diplom_weather.data.dto.WeatherDto

interface Repository {

    suspend fun getWeatherDto(city: String, language: String): WeatherDto

    suspend fun getSearchCityResult(text: String): List<SearchCityDto>
}