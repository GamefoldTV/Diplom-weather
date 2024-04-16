package ru.netology.diplom_weather.presentation.main

import android.util.Log
import androidx.compose.runtime.Stable
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.netology.diplom_weather.core.impl.BaseViewModel
import ru.netology.diplom_weather.data.Repository
import ru.netology.diplom_weather.data.dto.SearchCityDto
import ru.netology.diplom_weather.data.dto.WeatherDto
import ru.netology.diplom_weather.data.source.local.UserStorage

class MainViewModel(
    private val repository: Repository,
    private val userStorage: UserStorage,
) : BaseViewModel() {

    val weatherState = combine(userStorage.city, userStorage.language, ::merge).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000L),
        WeatherUiState.Loading
    )

    private suspend fun merge(
        city: String,
        language: String
    ): WeatherUiState {
        return try {
            Log.e("AndroidLogger", "Success")
            WeatherUiState.Success(repository.getWeatherDto(city, language))
        } catch (e: Exception) {
            errorHandler.handleError(e)
            Log.e("AndroidLogger", "Error")
            WeatherUiState.Error(e.cause?.let { errorHandler.getUserMessage(it) })
        }
    }

    private val _result = MutableStateFlow<List<SearchCityDto>>(listOf())

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    @OptIn(FlowPreview::class)
    val result = searchText
        .debounce(1000L)
        .onEach { _isSearching.emit(true) }
        .combine(_result) { text, result ->
            if (text.isBlank()) {
                Log.e("Result return", result.toString())
                result
            } else {
                try {
                    getSearchResult(text)
                } catch (e: Exception) {
                    errorHandler.handleError(e)
                    emptyList()
                }
            }
        }
        .onEach { _isSearching.emit(false) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(15000),
            _result.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    private suspend fun getSearchResult(text: String): List<SearchCityDto> {
        return repository.getSearchCityResult(text)
    }

    fun queryExecute(value: String) {
        viewModelScope.launch {
            userStorage.saveCity(value)
        }
    }
}

@Stable
sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Error(val message: String?) : WeatherUiState
    data class Success(val weatherDto: WeatherDto) : WeatherUiState
}