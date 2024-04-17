package ru.netology.diplom_weather.presentation.user.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import ru.netology.diplom_weather.R
import ru.netology.diplom_weather.presentation.ui.theme.Shapes



@Composable
fun SignInScreen(){



}

@Composable
private fun SignInUi(
    login: String,
    password: String,
    inProgress: Boolean,
    error: String?,
    onLoginChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignInClick: () -> Unit,
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
        error?.let {
            Text(
                modifier = Modifier
                    .background(Color.White.copy(0.6f), Shapes.small)
                    .padding(8.dp),
                text = "Что-то пошло не так",
                color = Color.Red,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        TextInput(
            value = login,
            inputType = InputType.Name,
            keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() }),
            changeText = onLoginChanged
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
    data object Name : InputType(
        label = "Username",
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
