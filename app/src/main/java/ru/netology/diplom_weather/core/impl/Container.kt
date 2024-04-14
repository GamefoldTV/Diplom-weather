package ru.netology.diplom_weather.core.impl

import kotlinx.coroutines.runBlocking

/**
 * Represents the current status of async fetch/operation.
 * @see Container.Pending
 * @see Container.Error
 * @see Container.Success
 */
sealed class Container<out T> {
    object Pending : Container<Nothing>()

    data class Error(
        val exception: Exception
    ) : Container<Nothing>()

    data class Success<T>(
        val value: T
    ) : Container<T>()
}
