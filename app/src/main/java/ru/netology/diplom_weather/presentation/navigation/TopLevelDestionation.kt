package ru.netology.diplom_weather.presentation.navigation

enum class TopLevelDestination(
    val route: String,
    val iconRawId: Int,
    val iconTextId: Int,
) {
    MENU(
        route = catalogRoute,
        iconRawId = R.raw.house,
        iconTextId = catalogR.string.menu,
    ),
//    USER(
//    ),
//    SETTINGS(
//    ),
}