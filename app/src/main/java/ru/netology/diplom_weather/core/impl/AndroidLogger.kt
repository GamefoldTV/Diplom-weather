package ru.netology.diplom_weather.core.impl

import android.util.Log
import ru.netology.diplom_weather.core.Logger

/**
 * Default implementation of [Logger] which sends all logs to the LogCat.
 */
class AndroidLogger : Logger {

    override fun log(message: String) {
        Log.d("AndroidLogger", message)
    }

    override fun err(exception: Throwable, message: String?) {
        Log.e("AndroidLogger", message, exception)
    }

}