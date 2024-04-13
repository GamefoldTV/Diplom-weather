package ru.netology.diplom_weather.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.StateFlow

class UserStorage(
    dataStore: DataStore<Preferences>,
) : Storage(dataStore) {

    private object PreferencesKeys {
        val CITY = stringPreferencesKey("city")
        val LANGUAGE = stringPreferencesKey("language")
    }

    val userCity: StateFlow<String?> = createStateFlow(PreferencesKeys.CITY)
    suspend fun saveCity(city: String) = savePreference(PreferencesKeys.CITY, city)

    val userLanguage: StateFlow<String?> = createStateFlow(PreferencesKeys.LANGUAGE)
    suspend fun saveLanguage(language: String) = savePreference(PreferencesKeys.LANGUAGE, language)
}


