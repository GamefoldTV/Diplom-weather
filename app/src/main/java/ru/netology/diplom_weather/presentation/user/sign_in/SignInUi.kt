package ru.netology.diplom_weather.presentation.user.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import ru.netology.diplom_weather.App
import ru.netology.diplom_weather.R
import ru.netology.diplom_weather.core.viewModelFactory
import ru.netology.diplom_weather.presentation.ui.theme.Shapes
import ru.netology.diplom_weather.presentation.user.usecases.SignInUseCase


@Composable
fun SignInScreen(
    onNavigateToSignUpClick: () -> Unit,
    onNavigateToProfile: () -> Unit,
) {

    val viewModel = viewModel<SignInViewModel>(factory = viewModelFactory {
        SignInViewModel(
            signInUseCase = SignInUseCase(
                firebaseAuth = App.appModule.firebaseAuth,
                userStorage = App.appModule.userStorage
            )
        )
    })

    LaunchedEffect(viewModel) {
        viewModel.signInComplete.collect {
            if (it) onNavigateToProfile()
        }
    }

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val inProgress by viewModel.inProgress.collectAsState()


    SignInUi(
        email = email,
        password = password,
        inProgress = inProgress,
        onEmailChanged = viewModel::changeEmail,
        onPasswordChanged = viewModel::changePassword,
        onSignInClick = viewModel::signIn,
        onNavigateToSignUpClick = onNavigateToSignUpClick,
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun SignInUi(
    email: String,
    password: String,
    inProgress: Boolean,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignInClick: () -> Unit,
    onNavigateToSignUpClick: () -> Unit,
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
                onSignInClick()
            }),
            focusRequester = passwordFocusRequester,
            changeText = onPasswordChanged
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !inProgress,
            onClick = {
                onSignInClick()
            },
        ) {
            Text(stringResource(R.string.sign_in), Modifier.padding(vertical = 8.dp))
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 48.dp),
            thickness = 1.dp,
            color = Color.White.copy(alpha = 0.3f)
        )
        val text = buildAnnotatedString {
            append(stringResource(R.string.do_not_have_account))
            val textTag = stringResource(R.string.sign_up)
            withAnnotation("tag", "annotation") {
                append(textTag)
            }
        }

        ClickableText(text) {
            text.getStringAnnotations(it, it).firstOrNull()?.tag?.let { tag ->
                onNavigateToSignUpClick()
            }
        }
    }
}

@Composable
fun TextInput(
    value: String,
    inputType: InputType,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions,
    changeText: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = changeText,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester()),
        leadingIcon = { Icon(imageVector = inputType.icon, null) },
        label = { Text(text = inputType.label) },
        shape = Shapes.large,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = keyboardActions
    )
}

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation,
) {
    data object Email : InputType(
        label = "Email",
        icon = Icons.Default.Person,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None,
    )

    data object Password : InputType(
        label = "Password",
        icon = Icons.Default.Lock,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = PasswordVisualTransformation()
    )
}
