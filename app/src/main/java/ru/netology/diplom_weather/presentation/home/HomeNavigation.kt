package ru.netology.diplom_weather.presentation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.netology.diplom_weather.presentation.main.MainViewModel

const val homeRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(viewModel: MainViewModel) {
    composable(
        route = homeRoute,
    ) {
        HomeScreen(viewModel)
    }
}