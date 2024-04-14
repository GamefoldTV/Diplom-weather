package ru.netology.diplom_weather

import android.app.Application
import ru.netology.diplom_weather.di.AppModule
import ru.netology.diplom_weather.di.CoreModule

class App : Application() {

    companion object {
        lateinit var appModule: AppModule
        lateinit var coreModule: CoreModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModule(applicationContext)
        coreModule = CoreModule(applicationContext)
    }

}