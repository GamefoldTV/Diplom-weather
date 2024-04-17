package ru.netology.diplom_weather.presentation.astrology

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import ru.netology.diplom_weather.R
import ru.netology.diplom_weather.presentation.main.MainViewModel
import ru.netology.diplom_weather.presentation.main.WeatherUiState
import ru.netology.diplom_weather.presentation.ui.theme.Padding
import ru.netology.diplom_weather.presentation.ui.theme.Padding.*
import ru.netology.diplom_weather.presentation.ui.theme.Shapes

@Composable
fun AstrologyScreen(viewModel: MainViewModel) {

    val uiState by viewModel.weatherState.collectAsState()

    AstrologyUi(
        uiState = uiState,
    )
}

@OptIn(UnstableApi::class)
@Composable
private fun AstrologyUi(
    uiState: WeatherUiState,
) {
    Column(
        modifier = Modifier.padding(top = TOP.value).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        when (val s = uiState) {
            is WeatherUiState.Error -> {

                Text(
                    text = buildAnnotatedString {
                        append(s.message)
                        append("\n")
                        withStyle(style = SpanStyle(fontSize = 21.sp)) {
                            append(stringResource(R.string.try_change_city))
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            WeatherUiState.Loading -> {
                CircularProgressIndicator()

            }

            is WeatherUiState.Success -> {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = Shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(0.3f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(M.value),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(M.value)
                    ) {
                        s.weatherDto.forecast.forecastday[0].astro.valueToDescription.forEach { (value, description) ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(id = description),
                                    color = Color.White,
                                    fontSize = 20.sp,
                                )
                                Text(
                                    text = value,
                                    color = Color.White,
                                    fontSize = 20.sp,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}