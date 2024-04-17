package ru.netology.diplom_weather.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(
    destinations: List<TopLevelDestination>,
    navController: NavController,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
) {
    NavigationBar {

        destinations.forEach { destination ->
            val selected = navController.currentBackStackEntryAsState().value?.destination
                .isTopLevelDestinationInHierarchy(destination)

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.image,
                        contentDescription = destination.description
                    )
                }
            )
        }
    }
}