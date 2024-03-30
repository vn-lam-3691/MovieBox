package com.vanlam.moviebox.main.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vanlam.moviebox.main.presentation.main.MainViewModel
import com.vanlam.moviebox.ui.theme.MovieBoxTheme
import com.vanlam.moviebox.utils.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetSystemBarColor(color = MaterialTheme.colorScheme.surface)

            MovieBoxTheme {
                val mainViewModel = hiltViewModel<MainViewModel>()
                val mainUiState = mainViewModel.mainUiState.collectAsState().value

                installSplashScreen().setKeepOnScreenCondition {
                    mainViewModel.showSplashScreenState.value
                }

                NavigationApp(mainUiState, mainViewModel::onEvent)
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

    NavHost(navController = navController, startDestination = Screen.MAIN_SCREEN.route) {
        composable(Screen.MAIN_SCREEN.route) {
            MediaMainScreen(
                navController,
                mainUiState,
                onEvent
            )
        }

        composable(Screen.DETAIL_SCREEN.route) {
//            DetailScreen()
        }

        composable(Screen.SEARCH_SCREEN.route) {
//            SearchScreen()
        }
    }
}
