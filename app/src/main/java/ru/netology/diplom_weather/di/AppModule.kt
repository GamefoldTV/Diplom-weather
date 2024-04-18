package ru.netology.diplom_weather.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import ru.netology.diplom_weather.data.Repository
import ru.netology.diplom_weather.data.RepositoryImpl
import ru.netology.diplom_weather.data.source.remote.WeatherApi
import ru.netology.diplom_weather.BuildConfig
import ru.netology.diplom_weather.data.source.local.UserStorage

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "weather_preferences")

interface AppModule {
    val repository: Repository
    val weatherApi: WeatherApi
    val firebaseAuth: FirebaseAuth
    val userStorage: UserStorage
}


fun AppModule(appContext: Context): AppModule {
    return object : AppModule {

        override val firebaseAuth: FirebaseAuth by lazy {
            FirebaseAuth.getInstance()
        }

        override val userStorage: UserStorage by lazy {
            UserStorage(appContext.dataStore)
        }

        override val weatherApi: WeatherApi by lazy {
            provideWeatherApi()
        }

        override val repository: Repository by lazy {
            RepositoryImpl(weatherApi, userStorage)
        }

    }
}

private fun provideWeatherApi(): WeatherApi {
    return retrofit(client = okhttp(loggingInterceptor()))
        .create(WeatherApi::class.java)
}


private fun retrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .addConverterFactory(converterFactory())
    .client(client)
    .build()

private fun converterFactory(): Converter.Factory {
    val contentType = "application/json".toMediaType()
    val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true

    }
    return json.asConverterFactory(contentType = contentType)
}

private fun okhttp(vararg interceptors: Interceptor): OkHttpClient = OkHttpClient.Builder()
    .apply {
        interceptors.forEach {
            this.addInterceptor(it)
        }
    }
    .build()

private fun loggingInterceptor() = HttpLoggingInterceptor()
    .apply {
        if (BuildConfig.DEBUG) {
            setLevel(HttpLoggingInterceptor.Level.BODY)
            setLevel(HttpLoggingInterceptor.Level.HEADERS)
        }
    }