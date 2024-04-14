package ru.netology.diplom_weather.core.impl

import android.content.Context
import android.widget.Toast
import ru.netology.diplom_weather.core.CommonUi

class AndroidCommonUi(
    private val applicationContext: Context
)  : CommonUi {
    override fun toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override suspend fun alertDialog(config: AlertDialogConfig): Boolean {
        TODO("Not yet implemented")
    }
}