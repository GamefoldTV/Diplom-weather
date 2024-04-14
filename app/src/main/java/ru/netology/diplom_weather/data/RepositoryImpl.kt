package ru.netology.diplom_weather.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import ru.netology.diplom_weather.core.BaseRetrofitDataSource
import ru.netology.diplom_weather.core.impl.Container
import ru.netology.diplom_weather.core.impl.createDefaultGlobalScope
import ru.netology.diplom_weather.data.dto.WeatherDto
import ru.netology.diplom_weather.data.source.local.UserStorage
import ru.netology.diplom_weather.data.source.remote.WeatherApi

class RepositoryImpl(
    private val weatherApi: WeatherApi,
    private val userStorage: UserStorage,
) : BaseRetrofitDataSource, Repository {

    val weather: StateFlow<WeatherDto?> = MutableStateFlow(null)

    suspend fun getWeatherDto(city: String, language: String){
        wrapRetrofitExceptions {
            weatherApi.getWeatherData(city = city, language = language)
        }
    }
}
//
//combine(userStorage.city, userStorage.language) { cityValue, languageValue ->
//    val weatherDto = wrapRetrofitExceptions {
//        weatherApi.getWeatherData(city = cityValue, language = languageValue)
//    }
//    Container.Success(weatherDto)
//}
//.catch {
//    Container.Error()
//}
//.stateIn(
//createDefaultGlobalScope(),
//SharingStarted.Eagerly,
//Container.Pending
//)