package ru.netology.diplom_weather.presentation.user

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.netology.diplom_weather.App

import ru.netology.diplom_weather.presentation.navigation.Graph
import ru.netology.diplom_weather.presentation.user.profile.navigateToProfile
import ru.netology.diplom_weather.presentation.user.profile.profileRoute
import ru.netology.diplom_weather.presentation.user.profile.profileScreen
import ru.netology.diplom_weather.presentation.user.sign_in.navigateToSignIn
import ru.netology.diplom_weather.presentation.user.sign_in.signInRoute
import ru.netology.diplom_weather.presentation.user.sign_in.signInScreen
import ru.netology.diplom_weather.presentation.user.sign_up.navigateToSignUp
import ru.netology.diplom_weather.presentation.user.sign_up.signUpScreen

@Composable
fun SharedScreen() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        route = Graph.USER,
        startDestination = signInRoute,
    ) {
        App.appModule.firebaseAuth.addAuthStateListener {
            when(it.currentUser){
                null -> navController.navigateToSignIn()
                else -> navController.navigateToProfile()
            }
        }

        signInScreen(
            onNavigateToSignUpClick = {
                navController.navigateToSignUp()
            },
            onNavigateToProfile = {
                navController.navigateToProfile()
            }
        )
        signUpScreen(
            onNavigateToSignIn = {
                navController.navigateToSignIn()
            }
        )
        profileScreen()
    }
}