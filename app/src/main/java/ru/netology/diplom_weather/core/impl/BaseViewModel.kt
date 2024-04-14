package ru.netology.diplom_weather.core.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel


open class BaseViewModel : ViewModel() {

    protected val viewModelScope: CoroutineScope by lazy {
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.e("error handler", exception.toString() + exception.cause.toString())
        }
        CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)
    }
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}