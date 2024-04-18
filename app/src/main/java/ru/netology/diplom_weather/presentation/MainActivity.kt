package ru.netology.diplom_weather.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.netology.diplom_weather.App
import ru.netology.diplom_weather.core.viewModelFactory
import ru.netology.diplom_weather.presentation.MainActivityUiState.*
import ru.netology.diplom_weather.presentation.home.homeScreen
import ru.netology.diplom_weather.presentation.home.navigateToHome
import ru.netology.diplom_weather.presentation.main.MainScreen
import ru.netology.diplom_weather.presentation.navigation.BottomNavBar
import ru.netology.diplom_weather.presentation.navigation.Graph
import ru.netology.diplom_weather.presentation.navigation.TopLevelDestination
import ru.netology.diplom_weather.presentation.ui.theme.DiplomweatherTheme

class MainActivity : ComponentActivity() {

    private val viewModel = viewModelFactory {
        MainActivityViewModel(
            firebaseAuth = App.appModule.firebaseAuth
        )
    }.create(MainActivityViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var uiState: MainActivityUiState by mutableStateOf(Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                Loading -> false
                is Success -> true
            }
        }

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