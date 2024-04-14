package ru.netology.diplom_weather.core

interface Logger {

    fun log(message: String)

    fun err(exception: Throwable, message: String? = null)

}