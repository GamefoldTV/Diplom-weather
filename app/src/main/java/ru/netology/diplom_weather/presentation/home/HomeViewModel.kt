package ru.netology.diplom_weather.presentation.home

import ru.netology.diplom_weather.core.impl.BaseViewModel
import ru.netology.diplom_weather.data.Repository
import ru.netology.diplom_weather.data.source.local.UserStorage

class HomeViewModel(
    private val repository: Repository,
    private val userStorage: UserStorage,
) : BaseViewModel() {

}