package ru.netology.diplom_weather.core.impl

/**
 * Любые исключения, управляемые внутри приложения.
 */
open class AppException(
    message: String = "",
    cause: Throwable? = null,
) : Exception(message, cause)

/**
 * Проблемы с подключением к Интернету
 */
class ConnectionException(cause: Exception) : AppException(cause = cause)

/**
 * Проблемы с удаленным обслуживанием
 */
open class RemoteServiceException(
    message: String,
    cause: Exception? = null
) : AppException(message, cause)

/**
 * Ошибки пришедшие с самого сервера
 */
class BackendException(
    val code: Int,
    message: String
) : RemoteServiceException(message)

/**
 * Ошибки в связи с пустыми полями авторизации
 */
class EmptyAuthField(
    message: String
) : AppException(message = message)

/**
 * Проблемы с аутентификацией.
 */
class AuthException(cause: Exception? = null) : AppException(cause = cause)

/**
 * Проблемы с чтением/записью данных в локальное хранилище.
 */
class StorageException(cause: Exception) : AppException(cause = cause)

/**
 * Исключение с удобным сообщением, которое можно безопасно отобразить пользователю.
 */
class UserFriendlyException(
    val userFriendlyMessage: String,
    cause: Exception,
) : AppException(cause.message ?: "", cause)

/**
 * Проблемы с парсингом данных с сервера
 */
class ParseBackendResponseException(cause: Exception) : AppException(cause = cause)

/**
 * Что-то не существует.
 */
class NotFoundException : AppException()