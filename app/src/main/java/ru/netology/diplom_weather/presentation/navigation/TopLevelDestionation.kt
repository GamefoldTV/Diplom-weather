package ru.netology.diplom_weather.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import ru.netology.diplom_weather.presentation.astrology.astrologyRoute
import ru.netology.diplom_weather.presentation.home.homeRoute

enum class TopLevelDestination(
    val route: String,
    val image: ImageVector,
    val description: String,
) {
    HOME(
        route = homeRoute,
        image = Icons.Filled.Home,
        description = Icons.Filled.Home.name,
    ),
    ASTROLOGY(
        route = astrologyRoute,
        image = Icons.Filled.Star,
        description = Icons.Filled.Star.name,
    ),
    SHARED(
        route = Graph.USER,
        image = Icons.Filled.AccountCircle,
        description = Icons.Filled.AccountCircle.name,
    )
}