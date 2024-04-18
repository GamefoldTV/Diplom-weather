package ru.netology.diplom_weather.presentation.user.usecases

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import ru.netology.diplom_weather.core.impl.AuthException
import ru.netology.diplom_weather.core.impl.EmptyAuthField
import ru.netology.diplom_weather.data.source.local.UserStorage

class SignInUseCase(
    private val firebaseAuth: FirebaseAuth,
    private val userStorage: UserStorage,
) {
    suspend fun signIn(email: String, password: String) {
        if (email.isBlank()) throw EmptyAuthField("Email is empty")
        if (password.isBlank()) throw EmptyAuthField("Password is empty")
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
//        val user = firebaseAuth.uid ?: throw AuthException()
//        userStorage.saveUser(user)
    }
}