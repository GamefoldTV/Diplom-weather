package ru.netology.diplom_weather.core

/**
* Глобальный обработчик ошибок по умолчанию для действий, обычно выполняемых через viewModelScope.
 */
interface ErrorHandler {

    fun handleError(exception: Throwable)

    fun getUserMessage(exception: Throwable): String

}