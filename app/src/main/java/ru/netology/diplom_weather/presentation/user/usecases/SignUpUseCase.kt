package ru.netology.diplom_weather.presentation.user.usecases

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import ru.netology.diplom_weather.core.impl.EmptyAuthField

class SignUpUseCase(
    private val firebaseAuth: FirebaseAuth,
) {
    suspend fun signUp(email: String, password: String) {
        if (email.isBlank()) throw EmptyAuthField("Email is empty")
        if (password.isBlank()) throw EmptyAuthField("Password is empty")
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }
}