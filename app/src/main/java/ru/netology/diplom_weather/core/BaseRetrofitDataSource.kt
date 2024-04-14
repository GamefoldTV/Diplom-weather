package ru.netology.diplom_weather.core

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response
import ru.netology.diplom_weather.core.impl.AppException
import ru.netology.diplom_weather.core.impl.BackendException
import ru.netology.diplom_weather.core.impl.ConnectionException
import ru.netology.diplom_weather.core.impl.ParseBackendResponseException
import java.io.IOException

/**
 * Базовый класс для всех источников OkHttp.
 */
interface BaseRetrofitDataSource {

    /**
     * Сопоставьте сетевые и синтаксические исключения с исключениями внутри приложения.
     * @throws RemoteServiceException
     * @throws ConnectionException
     * @throws ParseBackendResponseException
     * @throws AppException
     * @throws BackendException
     */
    suspend fun <T> wrapRetrofitExceptions(block: suspend () -> Response<T>): T {
        return try {
            val response = block()
            if (!response.isSuccessful) throw BackendException(
                code = response.code(),
                message = response.message()
            )
            response.body()!!
        } catch (e: AppException) {
            throw e
        } catch (e: SerializationException) {
            throw ParseBackendResponseException(e)
        } catch (e: IllegalArgumentException) {
            throw ParseBackendResponseException(e)
        } catch (e: IOException) {
            throw ConnectionException(e)
        } catch (e: HttpException) {
            throw createBackendException<T>(e)
        }
    }

    private fun <T> createBackendException(e: HttpException): Exception {
        return try {
            val errorBody: Response<T> =
                Json.decodeFromString(e.response()!!.errorBody()!!.string())
            BackendException(e.code(), errorBody.message())
        } catch (e: Exception) {
            throw ParseBackendResponseException(e)
        }
    }
}