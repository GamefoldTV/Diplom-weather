package ru.netology.diplom_weather.di

import android.content.Context
import ru.netology.diplom_weather.core.CommonUi
import ru.netology.diplom_weather.core.ErrorHandler
import ru.netology.diplom_weather.core.impl.AndroidCommonUi
import ru.netology.diplom_weather.core.impl.DefaultErrorHandler

interface CoreModule {
    val commonUi: CommonUi
    val errorHandler: ErrorHandler
}


fun CoreModule(appContext: Context): CoreModule {
    return object : CoreModule {
        override val commonUi: CommonUi
            get() = AndroidCommonUi(appContext)
        override val errorHandler: ErrorHandler
            get() = DefaultErrorHandler(appContext)

    }
}