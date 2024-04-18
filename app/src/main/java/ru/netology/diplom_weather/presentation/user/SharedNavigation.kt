package ru.netology.diplom_weather.presentation.user

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val sharedRoute = "shared_route"

fun NavController.navigateToShared(navOptions: NavOptions? = null) {
    this.navigate(sharedRoute, navOptions)
}

fun NavGraphBuilder.sharedScreen() {
    composable(
        route = sharedRoute,
    ) {
       SharedScreen()
    }
}