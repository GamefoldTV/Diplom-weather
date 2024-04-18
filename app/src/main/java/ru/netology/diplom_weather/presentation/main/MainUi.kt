package ru.netology.diplom_weather.presentation.main

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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import ru.netology.diplom_weather.App
import ru.netology.diplom_weather.R
import ru.netology.diplom_weather.core.viewModelFactory
import ru.netology.diplom_weather.presentation.astrology.astrologyScreen
import ru.netology.diplom_weather.presentation.astrology.navigateToAstrology
import ru.netology.diplom_weather.presentation.home.homeScreen
import ru.netology.diplom_weather.presentation.home.navigateToHome
import ru.netology.diplom_weather.presentation.navigation.BottomNavBar
import ru.netology.diplom_weather.presentation.navigation.Graph
import ru.netology.diplom_weather.presentation.navigation.TopLevelDestination
import ru.netology.diplom_weather.presentation.user.navigateToShared
import ru.netology.diplom_weather.presentation.user.sharedScreen

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
) {
    val viewModel = viewModel<MainViewModel>(factory = viewModelFactory {
        MainViewModel(
            repository = App.appModule.repository,
            userStorage = App.appModule.userStorage
        )
    })

    MainUi(
        viewModel = viewModel,
        navController = navController,
    )
}

@OptIn(UnstableApi::class)
@Composable
private fun MainUi(
    navController: NavHostController,
    viewModel: MainViewModel
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
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = { Box {} },
        bottomBar = {
            BottomNavBar(
                destinations = TopLevelDestination.entries,
                navController = navController,
                onNavigateToDestination = { destination ->
                    val topLevelOptions = navOptions {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    when (destination) {
                        TopLevelDestination.HOME -> navController.navigateToHome(
                            topLevelOptions
                        )

                        TopLevelDestination.ASTROLOGY -> navController.navigateToAstrology(
                            topLevelOptions
                        )

                        TopLevelDestination.SHARED -> navController.navigateToShared(
                            topLevelOptions
                        )
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(
                navController = navController,
                route = Graph.MAIN,
                startDestination = TopLevelDestination.HOME.route,
            ) {
                homeScreen(viewModel)
                astrologyScreen(viewModel)
                sharedScreen()
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