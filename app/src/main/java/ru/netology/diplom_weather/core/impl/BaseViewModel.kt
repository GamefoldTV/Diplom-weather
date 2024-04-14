package ru.netology.diplom_weather.core.impl

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import ru.netology.diplom_weather.App.Companion.coreModule


open class BaseViewModel : ViewModel() {

    protected val errorHandler = coreModule.errorHandler

    protected val viewModelScope: CoroutineScope by lazy {
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            errorHandler.handleError(exception)
        }
        CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)
    }
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}