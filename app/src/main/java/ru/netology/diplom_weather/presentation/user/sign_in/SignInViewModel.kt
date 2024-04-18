package ru.netology.diplom_weather.presentation.user.sign_in

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.netology.diplom_weather.core.impl.BaseViewModel
import ru.netology.diplom_weather.core.impl.EmptyAuthField
import ru.netology.diplom_weather.presentation.user.usecases.SignInUseCase


class SignInViewModel (
    private val signInUseCase: SignInUseCase,
) : BaseViewModel() {


    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val inProgress = MutableStateFlow(false)


    private val _signInComplete = MutableSharedFlow<Boolean>()
    val signInComplete: Flow<Boolean> = _signInComplete

    fun changeEmail(value: String){
        email.value = value
    }

    fun changePassword(value: String){
        password.value = value
    }

    fun signIn(){
        viewModelScope.launch {
            try {
                inProgress.value = true
                signInUseCase.signIn(email.value, password.value)
                _signInComplete.emit(true)
            } catch (e: EmptyAuthField){
                errorHandler.handleError(e)
            } catch (e: Exception){
                errorHandler.handleError(e)
            } finally {
                inProgress.value = false
            }
        }
    }

}
