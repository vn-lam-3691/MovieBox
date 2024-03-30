package com.vanlam.moviebox.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LiveTv
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object HOME_SCREEN: BottomBarScreen("home", "Home", Icons.Rounded.Home)
    object POPULAR_MOVIE: BottomBarScreen("popular", "Popular", Icons.Rounded.LocalFireDepartment)
    object TV_SHOW: BottomBarScreen("tv_show", "TV Show", Icons.Rounded.LiveTv)
    object WATCH_LIST: BottomBarScreen("watch_list", "Watch list", Icons.Rounded.Favorite)
}