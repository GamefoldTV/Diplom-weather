package ru.netology.diplom_weather.data

import ru.netology.diplom_weather.core.BaseRetrofitDataSource
import ru.netology.diplom_weather.data.dto.SearchCityDto
import ru.netology.diplom_weather.data.dto.WeatherDto
import ru.netology.diplom_weather.data.source.local.UserStorage
import ru.netology.diplom_weather.data.source.remote.WeatherApi

class RepositoryImpl(
    private val weatherApi: WeatherApi,
    private val userStorage: UserStorage,
) : BaseRetrofitDataSource, Repository {

    override suspend fun getWeatherDto(city: String, language: String): WeatherDto {
        return wrapRetrofitExceptions {
            weatherApi.getWeatherData(city = city, language = language)
        }
    }

    override suspend fun getSearchCityResult(text: String): List<SearchCityDto> {
        return wrapRetrofitExceptions {
            weatherApi.getSearchCities(city = text)
        }
    }

}