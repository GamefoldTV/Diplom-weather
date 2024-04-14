package ru.netology.diplom_weather.di

import android.content.Context
import ru.netology.diplom_weather.core.CommonUi
import ru.netology.diplom_weather.core.impl.AndroidCommonUi

interface CoreModule {
    val commonUi: CommonUi
}


fun CoreModule(appContext: Context): CoreModule {
    return object : CoreModule {
        override val commonUi: CommonUi
            get() = AndroidCommonUi(appContext)


    }
}