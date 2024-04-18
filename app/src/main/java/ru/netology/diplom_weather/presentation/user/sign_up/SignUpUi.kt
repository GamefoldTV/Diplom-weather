import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.netology.diplom_weather.App
import ru.netology.diplom_weather.R
import ru.netology.diplom_weather.core.viewModelFactory
import ru.netology.diplom_weather.presentation.user.sign_in.InputType
import ru.netology.diplom_weather.presentation.user.sign_in.TextInput
import ru.netology.diplom_weather.presentation.user.sign_up.SignUpViewModel
import ru.netology.diplom_weather.presentation.user.usecases.SignUpUseCase

@Composable
fun SignUpScreen(
    onNavigateToSignIn: () -> Unit,
) {

    val viewModel = viewModel<SignUpViewModel>(factory = viewModelFactory {
        SignUpViewModel(
            signUpUseCase = SignUpUseCase(App.appModule.firebaseAuth)
        )
    })

    LaunchedEffect(viewModel){
        viewModel.signUpComplete.collect {
            if(it) onNavigateToSignIn()
        }
    }

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val inProgress by viewModel.inProgress.collectAsState()

    SignUpUi(
        email = email,
        password = password,
        inProgress = inProgress,
        onEmailChanged = viewModel::changeEmail,
        onPasswordChanged = viewModel::changePassword,
        onSignUpClick = viewModel::signUp
    )
}

@Composable
private fun SignUpUi(
    email: String,
    password: String,
    inProgress: Boolean,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignUpClick: () -> Unit,
) {
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    Column(
        Modifier
            .imePadding()
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInput(
            value = email,
            inputType = InputType.Email,
            keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() }),
            changeText = onEmailChanged
        )
        TextInput(
            value = password,
            inputType = InputType.Password,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                onSignUpClick()
            }),
            focusRequester = passwordFocusRequester,
            changeText = onPasswordChanged
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !inProgress,
            onClick = {
                onSignUpClick()
            },
        ) {
            Text(stringResource(R.string.sign_in), Modifier.padding(vertical = 8.dp))
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 48.dp),
            thickness = 1.dp,
            color = Color.White.copy(alpha = 0.3f)
        )
    }
}