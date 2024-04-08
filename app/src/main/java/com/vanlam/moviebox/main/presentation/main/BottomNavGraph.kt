package com.vanlam.moviebox.main.presentation.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vanlam.moviebox.main.presentation.home.MediaHomeScreen
import com.vanlam.moviebox.main.presentation.popularMovie.PopularMovieScreen
import com.vanlam.moviebox.main.presentation.tvshow.TvShowScreen
import com.vanlam.moviebox.utils.BottomBarScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    bottomNavController: NavHostController,
    mainUiState: MainUiState,
    mainUiEvent: (MainUiEvent) -> Unit
) {

    NavHost(
        navController = bottomNavController,
        startDestination = BottomBarScreen.HOME_SCREEN.route
    ) {
        composable(route = BottomBarScreen.HOME_SCREEN.route) {
            MediaHomeScreen(
                navController = navController,
                bottomNavController = bottomNavController,
                mainUiState = mainUiState,
                mainUiEvent = mainUiEvent
            )
        }

        composable(route = BottomBarScreen.POPULAR_MOVIE.route) {
            PopularMovieScreen(
                navController = navController,
                mainUiState = mainUiState,
                mainUiEvent = mainUiEvent
            )
        }

        composable(route = BottomBarScreen.TV_SHOW.route) {
            TvShowScreen(
                navController = navController,
                mainUiState = mainUiState,
                mainUiEvent = mainUiEvent
            )
        }

        composable(route = BottomBarScreen.WATCH_LIST.route) {

        }
    }
}