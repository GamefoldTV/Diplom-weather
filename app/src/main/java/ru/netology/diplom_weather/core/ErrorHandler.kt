package ru.netology.diplom_weather.core

/**
 * Default global error handler for actions executed usually via viewModelScope.
 */
interface ErrorHandler {

    fun handleError(exception: Throwable)

    fun getUserMessage(exception: Throwable): String

}