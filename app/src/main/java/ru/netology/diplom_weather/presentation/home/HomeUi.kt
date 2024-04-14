package ru.netology.diplom_weather.presentation.home

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toDrawable
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import ru.netology.diplom_weather.R
import ru.netology.diplom_weather.data.dto.SearchCityDto
import ru.netology.diplom_weather.presentation.main.MainUiState
import ru.netology.diplom_weather.presentation.main.MainViewModel


@Composable
fun HomeScreen(viewModel: MainViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    val result by viewModel.result.collectAsState()
    val searchText by viewModel.searchText.collectAsState()

    HomeUi(
        uiState = uiState,
        result = result,
        searchText = searchText,
        onSearchTextChange = viewModel::onSearchTextChange,
    )
}

@OptIn(UnstableApi::class)
@Composable
private fun HomeUi(
    uiState: MainUiState,
    result: List<SearchCityDto>,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
) {

    val context = LocalContext.current

    val exoPlayer = remember {
        context.buildExoPlayer(uri = Uri.parse("android.resource://raw/" + R.raw.clouds))
    }

    DisposableEffect(
        key1 = exoPlayer
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { it.buildPlayerView(exoPlayer) },
    )

    Scaffold(
        topBar = {

        },
        bottomBar = { Box {} }
    ) {
        Box(Modifier.padding(it)) {

            SearchableExposedDropdownMenuBox(
                items = result,
                searchText = searchText,
                onSearchTextChange = onSearchTextChange
            )
        }
    }


}


@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableExposedDropdownMenuBox(
    items: List<SearchCityDto>,
    searchText: String,
    onSearchTextChange: (String) -> Unit,

    ) {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf("Americano", "Cappuccino", "Espresso", "Latte", "Mocha")
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = searchText,
                onValueChange = onSearchTextChange,
                label = { Text(text = "Start typing the name of the coffee") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            if (items.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        // We shouldn't hide the menu when the user enters/removes any character
                    }
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.name) },
                            onClick = {
                                onSearchTextChange(item.name)
                                expanded = false
                                Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun Context.buildExoPlayer(uri: Uri) =
    ExoPlayer.Builder(this).build().apply {
        setMediaItem(MediaItem.fromUri(uri))
        repeatMode = Player.REPEAT_MODE_ALL
        playWhenReady = true
        prepare()
    }

@UnstableApi
private fun Context.buildPlayerView(exoPlayer: ExoPlayer) =
    PlayerView(this).apply {
        foreground = Color.Black.copy(0.3f).toArgb().toDrawable()
        player = exoPlayer
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        useController = false
        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
    }

//@Composable
//@Preview(showSystemUi = true)
//private fun PreviewHomeUi() {
//
//    HomeUi(MainUiState.Loading, result, searchText)
//}