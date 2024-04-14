package com.vanlam.moviebox.utils

sealed class Screen(val route: String) {
    object MAIN_SCREEN: Screen("main_screen")
    object DETAIL_SCREEN: Screen("details")
    object SEARCH_SCREEN: Screen("search_screen")
    object VIDEO_SCREEN: Screen("video_screen")
}