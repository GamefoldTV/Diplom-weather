package ru.netology.diplom_weather

import android.app.Application
import ru.netology.diplom_weather.di.AppModule

class App : Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModule(applicationContext)
    }

}