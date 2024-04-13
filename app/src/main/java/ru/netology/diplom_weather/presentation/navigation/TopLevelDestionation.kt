package ru.netology.diplom_weather.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import ru.netology.diplom_weather.R
import ru.netology.diplom_weather.presentation.home.homeRoute

enum class TopLevelDestination(
    val route: String,
    val image: ImageVector,
    @StringRes val description: Int,
) {
    HOME(
        route = homeRoute,
        image = Icons.Filled.Home,
        description = R.string.app_name,
    ),
//    USER(
//    ),
//    SETTINGS(
//    ),
}