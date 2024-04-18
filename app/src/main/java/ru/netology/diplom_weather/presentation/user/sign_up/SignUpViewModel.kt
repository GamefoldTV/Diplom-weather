package ru.netology.diplom_weather.presentation.user.sign_up

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.netology.diplom_weather.core.impl.BaseViewModel
import ru.netology.diplom_weather.core.impl.EmptyAuthField
import ru.netology.diplom_weather.presentation.user.usecases.SignInUseCase
import ru.netology.diplom_weather.presentation.user.usecases.SignUpUseCase

class SignUpViewModel (
    private val signUpUseCase: SignUpUseCase,
) : BaseViewModel(){


    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val inProgress = MutableStateFlow(false)

    private val _signUpComplete = MutableSharedFlow<Boolean>()
    val signUpComplete: Flow<Boolean> = _signUpComplete

    fun changeEmail(value: String){
        email.value = value
    }

    fun changePassword(value: String){
        password.value = value
    }

    fun signUp(){
        viewModelScope.launch {
            try {
                inProgress.value = true
                signUpUseCase.signUp(email.value, password.value)
                _signUpComplete.emit(true)
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