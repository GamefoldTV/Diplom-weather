package ru.netology.diplom_weather.presentation.home

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import ru.netology.diplom_weather.App.Companion.appModule
import ru.netology.diplom_weather.R
import ru.netology.diplom_weather.core.viewModelFactory
import ru.netology.diplom_weather.presentation.main.MainUiState


@Composable
fun HomeScreen(uiState: MainUiState){

    HomeUi(uiState)
}

@OptIn(UnstableApi::class)
@Composable
private fun HomeUi(uiState: MainUiState){

    val context = LocalContext.current
//
//    val exoPlayer = remember {
//        context.buildExoPlayer(uri = Uri.parse("android.resource://raw/" + R.raw.clouds))
//    }
//
//    DisposableEffect(
//        key1 = exoPlayer
//    ) {
//        onDispose {
//            exoPlayer.release()
//        }
//    }
//    AndroidView(
//        modifier = Modifier.fillMaxSize(),
//        factory = { it.buildPlayerView(exoPlayer) },
//    )

    Scaffold(
        topBar = {

        },
        bottomBar = { Box{} }
    ) {
        Box(Modifier.padding(it)){

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

@Composable
@Preview(showSystemUi = true)
private fun PreviewHomeUi(){

    HomeUi(MainUiState.Loading)
}