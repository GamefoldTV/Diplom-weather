package ru.netology.diplom_weather.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import ru.netology.diplom_weather.presentation.home.homeScreen
import ru.netology.diplom_weather.presentation.home.navigateToHome
import ru.netology.diplom_weather.presentation.navigation.BottomNavBar
import ru.netology.diplom_weather.presentation.navigation.Graph
import ru.netology.diplom_weather.presentation.navigation.TopLevelDestination

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
) {

    MainUi(navController = navController)
}

@Composable
private fun MainUi(navController: NavHostController) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { Box {} },
        bottomBar = {
            BottomNavBar(
                destinations = TopLevelDestination.entries,
                navController = navController,
                onNavigateToDestination = { destination ->
                    val topLevelOptions = navOptions {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    when (destination) {
                        TopLevelDestination.HOME -> navController.navigateToHome(
                            topLevelOptions
                        )
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {

            NavHost(
                navController = navController,
                route = Graph.MAIN,
                startDestination = TopLevelDestination.HOME.route,
            ) {
                homeScreen()
            }
        }
    }

}