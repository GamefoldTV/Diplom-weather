package ru.netology.diplom_weather.presentation.user.sign_in

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val signInRoute = "sign_in_route"

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    this.navigate(signInRoute, navOptions)
}

fun NavGraphBuilder.signInScreen() {
    composable(
        route = signInRoute,
    ) {
        SignInScreen()
    }
}