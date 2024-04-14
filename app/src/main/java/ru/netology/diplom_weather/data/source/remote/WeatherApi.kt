package ru.netology.diplom_weather.data.source.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.netology.diplom_weather.BuildConfig
import ru.netology.diplom_weather.data.dto.WeatherDto

interface WeatherApi {

    @GET("forecast.json")
    suspend fun getWeatherData(
        @Query("key") key: String = BuildConfig.API_KEY,
        @Query("q") city: String,
        @Query("days") days: String = "3",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
        @Query("lang") language: String,
    ): Response<WeatherDto>

}