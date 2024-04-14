package ru.netology.diplom_weather.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import ru.netology.diplom_weather.App
import ru.netology.diplom_weather.core.viewModelFactory
import ru.netology.diplom_weather.presentation.home.homeScreen
import ru.netology.diplom_weather.presentation.home.navigateToHome
import ru.netology.diplom_weather.presentation.main.MainScreen
import ru.netology.diplom_weather.presentation.navigation.BottomNavBar
import ru.netology.diplom_weather.presentation.navigation.Graph
import ru.netology.diplom_weather.presentation.navigation.TopLevelDestination
import ru.netology.diplom_weather.presentation.ui.theme.DiplomweatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {

            DiplomweatherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}