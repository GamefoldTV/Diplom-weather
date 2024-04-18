package ru.netology.diplom_weather.presentation.user.sign_up

import SignUpScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val signUpRoute = "sign_up_route"

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpRoute, navOptions)
}

fun NavGraphBuilder.signUpScreen(onNavigateToSignIn: () -> Unit) {
    composable(
        route = signUpRoute,
    ) {
        SignUpScreen(onNavigateToSignIn = onNavigateToSignIn)
    }
}