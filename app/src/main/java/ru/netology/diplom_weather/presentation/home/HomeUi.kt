package ru.netology.diplom_weather.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.netology.diplom_weather.App.Companion.appModule
import ru.netology.diplom_weather.core.viewModelFactory


@Composable
fun HomeScreen(){
    val viewModel = viewModel<HomeViewModel>(factory = viewModelFactory {
        HomeViewModel(
            repository = appModule.repository,
            userStorage = appModule.userStorage
        )
    })

    HomeUi()
}

@Composable
private fun HomeUi(){




}

@Composable
@Preview
private fun PreviewHomeUi(){

    HomeUi()
}