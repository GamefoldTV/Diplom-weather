package ru.netology.diplom_weather.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

abstract class Storage(private val dataStore: DataStore<Preferences>) {

    fun <T> createStateFlow(
        preferencesKey: Preferences.Key<T>,
        defaultValue: T? = null,
    ): StateFlow<T?> {
        return dataStore.data
            .map { preferences ->
                preferences[preferencesKey]
            }
            .catch { it.printStackTrace() }
            .stateIn(
                CoroutineScope(Dispatchers.IO),
                SharingStarted.Eagerly,
                defaultValue
            )
    }

    suspend fun <T> savePreference(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun <T> clearPreference(key: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }
}