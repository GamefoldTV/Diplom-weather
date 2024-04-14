package ru.netology.diplom_weather.core


interface Resources {

    fun getString(id: Int): String

    fun getString(id: Int, vararg placeholders: Any): String

}