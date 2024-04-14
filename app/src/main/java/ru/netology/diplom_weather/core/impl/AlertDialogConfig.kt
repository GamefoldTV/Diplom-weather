package ru.netology.diplom_weather.core.impl


data class AlertDialogConfig(
    val title: String,
    val message: String,
    val positiveButton: String,
    val negativeButton: String? = null,
)