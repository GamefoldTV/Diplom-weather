package ru.netology.diplom_weather.presentation.user.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import ru.netology.diplom_weather.App

@Composable
fun ProfileScreen() {

    val userEmail = remember {
        mutableStateOf(App.appModule.firebaseAuth.currentUser?.email)
    }

    ProfileUi(userEmail = userEmail.value)

}


@Composable
private fun ProfileUi(userEmail: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(userEmail ?: "Empty Email")
    }
}