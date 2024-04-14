package ru.netology.diplom_weather.presentation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.netology.diplom_weather.presentation.main.MainUiState

const val homeRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(uiState: MainUiState) {
    composable(
        route = homeRoute,
    ) {
        HomeScreen(uiState)
    }
}