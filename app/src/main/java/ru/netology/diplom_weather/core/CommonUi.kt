package ru.netology.diplom_weather.core

import ru.netology.diplom_weather.core.impl.AlertDialogConfig

interface CommonUi {

    fun toast(message: String)

    suspend fun alertDialog(config: AlertDialogConfig): Boolean

}
