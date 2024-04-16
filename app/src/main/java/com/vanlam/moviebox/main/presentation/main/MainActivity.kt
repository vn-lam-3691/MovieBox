package com.vanlam.moviebox.main.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vanlam.moviebox.main.domain.model.Media
import com.vanlam.moviebox.media_details.presentation.detail.DetailScreenEvent
import com.vanlam.moviebox.media_details.presentation.detail.DetailsScreen
import com.vanlam.moviebox.media_details.presentation.detail.DetailsViewModel
import com.vanlam.moviebox.media_details.presentation.watch_video.VideoMediaScreen
import com.vanlam.moviebox.search_media.presentation.SearchScreen
import com.vanlam.moviebox.ui.theme.MovieBoxTheme
import com.vanlam.moviebox.ui.theme.MyMaterialTheme
import com.vanlam.moviebox.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetSystemBarColor(color = MaterialTheme.colorScheme.onBackground)

            MovieBoxTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MyMaterialTheme.appColor.backgroundColor)
                ) {
                    val mainViewModel = hiltViewModel<MainViewModel>()
                    val mainUiState = mainViewModel.mainUiState.collectAsState().value

                    installSplashScreen().setKeepOnScreenCondition {
                        mainViewModel.showSplashScreenState.value
                    }

                    NavigationApp(mainUiState, mainViewModel::onEvent)
                }
            }
        }
    }

    @Composable
    fun SetSystemBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        LaunchedEffect(key1 = color) {
            systemUiController.setSystemBarsColor(color)
        }
    }
}

@Composable
fun NavigationApp(
    mainUiState: MainUiState,
    onEvent: (MainUiEvent) -> Unit
) {
    val navController = rememberNavController()

    val detailsViewModel = hiltViewModel<DetailsViewModel>()
    val detailUiState = detailsViewModel.detailState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = Screen.MAIN_SCREEN.route
    ) {
        composable(
            Screen.MAIN_SCREEN.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            }
        ) {
            MediaMainScreen(
                navController,
                mainUiState,
                onEvent
            )
        }

        composable(
            Screen.DETAIL_SCREEN.route
        ) {
            val media = navController.previousBackStackEntry?.savedStateHandle?.get<Media>("media")

            media?.let {
                LaunchedEffect(key1 = true) {
                    detailsViewModel.onEvent(
                        DetailScreenEvent.SetDataAndLoad(
                            media = media
                        )
                    )
                }

                DetailsScreen(
                    navController = navController,
                    media = media,
                    detailsUiState = detailUiState,
                    onDetailsEvent = detailsViewModel::onEvent,
                    onMainEvent = onEvent
                )
            }
        }

        composable(Screen.SEARCH_SCREEN.route) {
            SearchScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.VIDEO_SCREEN.route + "/{videoId}",
            arguments = listOf(
                navArgument(name = "videoId") {
                    type = NavType.StringType
                }
            )
        ) { backstackEntry ->
            val videoId = backstackEntry.arguments?.getString("videoId")

            if (videoId != null) {
                VideoMediaScreen(
                    navController = navController,
                    lifecycleOwner = LocalLifecycleOwner.current,
                    videoId = videoId
                )
            }
        }
    }
}
