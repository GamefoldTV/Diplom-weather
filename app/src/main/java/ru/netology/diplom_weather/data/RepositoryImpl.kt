package ru.netology.diplom_weather.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.netology.diplom_weather.data.dto.WeatherDto
import ru.netology.diplom_weather.data.source.remote.WeatherApi

class RepositoryImpl(
    private val weatherApi: WeatherApi,
) : Repository {

    override val weather: Flow<WeatherDto?> = flow { emit(null) }

    override fun getWeatherData() {

    }


}