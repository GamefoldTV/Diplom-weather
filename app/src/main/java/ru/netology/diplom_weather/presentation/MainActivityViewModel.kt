package ru.netology.diplom_weather.presentation

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import ru.netology.diplom_weather.App
import ru.netology.diplom_weather.core.impl.BaseViewModel

class MainActivityViewModel(
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel() {

    val uiState: StateFlow<MainActivityUiState> = flow<MainActivityUiState> {

        firebaseAuth.addAuthStateListener {

            when (it.currentUser) {
                null -> MainActivityUiState.Success(false)
                else -> MainActivityUiState.Success(true)
            }
        }

    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val auth: Boolean) : MainActivityUiState
}