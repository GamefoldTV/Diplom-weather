package ru.netology.diplom_weather.presentation.home

import androidx.annotation.OptIn
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import coil.compose.AsyncImage
import ru.netology.diplom_weather.R
import ru.netology.diplom_weather.data.dto.ForecastDto
import ru.netology.diplom_weather.data.dto.SearchCityDto
import ru.netology.diplom_weather.data.dto.WeatherDto
import ru.netology.diplom_weather.presentation.main.MainViewModel
import ru.netology.diplom_weather.presentation.main.WeatherUiState
import ru.netology.diplom_weather.presentation.ui.theme.Padding.M
import ru.netology.diplom_weather.presentation.ui.theme.Padding.S
import ru.netology.diplom_weather.presentation.ui.theme.Padding.TOP
import ru.netology.diplom_weather.presentation.ui.theme.Padding.XS
import ru.netology.diplom_weather.presentation.ui.theme.Shapes
import java.time.LocalDateTime


@Composable
fun HomeScreen(viewModel: MainViewModel) {

    val uiState by viewModel.weatherState.collectAsState()
    val result by viewModel.result.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    HomeUi(
        uiState = uiState,
        result = result,
        onQueryChange = viewModel::onSearchTextChange,
        isSearching = isSearching,
        queryExecute = viewModel::queryExecute
    )
}

@OptIn(UnstableApi::class)
@Composable
private fun HomeUi(
    uiState: WeatherUiState,
    result: List<SearchCityDto>,
    onQueryChange: (String) -> Unit,
    isSearching: Boolean,
    queryExecute: (String) -> Unit,
) {
    var isSearchActive by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            EmbeddedSearchBar(
                onQueryChange = onQueryChange,
                isSearchActive = isSearchActive,
                onActiveChanged = { isSearchActive = it },
                result = result,
                queryExecute = queryExecute,
                isLoading = isSearching
            )
        },
        bottomBar = { Box {} }
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(M.value)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(M.value),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    RegionAndCountry(
                        modifier = Modifier.fillMaxWidth(),
                        weather = s.weatherDto
                    )
                    WeatherCard(
                        modifier = Modifier.fillMaxWidth(),
                        weather = s.weatherDto
                    )
                    CurrentDayDetail(
                        modifier = Modifier.fillMaxWidth(),
                        forecast = s.weatherDto.forecast
                    )
                }
            }
        }
    }
}

@Composable
private fun CurrentDayDetail(
    modifier: Modifier,
    forecast: ForecastDto
) {
    Card(
        modifier = modifier,
        shape = Shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(0.3f)
        )
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = M.value),
            horizontalArrangement = Arrangement.spacedBy(S.value)
        ) {
            items(
                forecast.forecastday.flatMap { it.hour }.drop(LocalDateTime.now().hour).take(24)
            ) {
                Column(
                    modifier = Modifier.padding(XS.value),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = it.date.hour.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    AsyncImage(
                        modifier = Modifier.size(32.dp),
                        model = "https:\\" + it.condition.icon,
                        contentDescription = ""
                    )
                    Text(
                        text = it.temp_c.toInt().toString() + "°",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
            }
        }

    }
}

@Composable
private fun RegionAndCountry(
    modifier: Modifier,
    weather: WeatherDto
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        with(weather.location) {
            listOfNotNull(region, country).forEach {
                Text(
                    text = it,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun WeatherCard(
    modifier: Modifier,
    weather: WeatherDto
) {

    Card(
        modifier = modifier,
        shape = Shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(M.value)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(M.value, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weather.location.name ?: "",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
            )
            Text(
                text = weather.current.temp_c.toString() + "°",
                color = Color.White,
                fontSize = 64.sp
            )
            Text(
                text = weather.forecast.forecastday[0].day.condition.text,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(id = R.string.min) + weather.forecast.forecastday[0].day.mintemp_c.toString() + "°",
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )

                Text(
                    text = stringResource(id = R.string.avg) + weather.forecast.forecastday[0].day.avgtemp_c.toString() + "°",
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )

                Text(
                    text = stringResource(id = R.string.max)  + weather.forecast.forecastday[0].day.maxtemp_c.toString() + "°",
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmbeddedSearchBar(
    onQueryChange: (String) -> Unit,
    isSearchActive: Boolean,
    onActiveChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onSearch: ((String) -> Unit)? = null,
    result: List<SearchCityDto>,
    queryExecute: (String) -> Unit,
    isLoading: Boolean
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val activeChanged: (Boolean) -> Unit = { active ->
        searchQuery = ""
        onQueryChange("")
        onActiveChanged(active)
    }
    SearchBar(
        query = searchQuery,
        onQueryChange = { query ->
            searchQuery = query
            onQueryChange(query)
        },
        onSearch = onSearch ?: { activeChanged(false) },
        active = isSearchActive,
        onActiveChange = activeChanged,
        modifier = if (isSearchActive) {
            modifier
                .animateContentSize(spring(stiffness = Spring.StiffnessHigh))
        } else {
            modifier
                .padding(start = M.value, top = TOP.value, end = M.value, bottom = M.value)
                .fillMaxWidth()
                .animateContentSize(spring(stiffness = Spring.StiffnessHigh))
        },
        placeholder = { Text(stringResource(R.string.search)) },
        leadingIcon = {
            if (isSearchActive) {
                IconButton(
                    onClick = { activeChanged(false) },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        trailingIcon = if (isSearchActive && searchQuery.isNotEmpty()) {
            {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    IconButton(
                        onClick = {
                            searchQuery = ""
                            onQueryChange("")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        } else {
            null
        },
        colors = SearchBarDefaults.colors(
            containerColor = if (isSearchActive) {
                MaterialTheme.colorScheme.background
            } else {
                MaterialTheme.colorScheme.surfaceContainerLow
            },
        ),
        tonalElevation = 0.dp,
        windowInsets = if (isSearchActive) {
            SearchBarDefaults.windowInsets
        } else {
            WindowInsets(0.dp)
        }
    ) {

        if (result.isNotEmpty()) {
            result.forEach { item ->
                Card(
                    onClick = {
                        searchQuery = item.name
                        onQueryChange(item.name)
                        queryExecute(item.url)
                        activeChanged(false)
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = S.value, vertical = M.value)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(end = XS.value),
                            text = item.name
                        )
                        Text(
                            text = item.region,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                HorizontalDivider(
                    thickness = 1.dp
                )
            }
        } else {
            Text(text = stringResource(id = R.string.not_found))
        }
    }
}


//@Composable
//@Preview(showSystemUi = true)
//private fun PreviewHomeUi() {
//
//    HomeUi(MainUiState.Loading, result, searchText)
//}