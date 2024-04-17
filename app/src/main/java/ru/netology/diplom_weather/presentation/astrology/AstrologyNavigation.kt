package ru.netology.diplom_weather.presentation.astrology

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.netology.diplom_weather.presentation.main.MainViewModel

const val astrologyRoute = "astrology_route"

fun NavController.navigateToAstrology(navOptions: NavOptions? = null) {
    this.navigate(astrologyRoute, navOptions)
}

fun NavGraphBuilder.astrologyScreen(viewModel: MainViewModel) {
    composable(
        route = astrologyRoute,
    ) {
        AstrologyScreen(viewModel)
    }
}