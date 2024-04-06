package com.vanlam.moviebox.main.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vanlam.moviebox.ui.theme.MyMaterialTheme
import com.vanlam.moviebox.utils.BottomBarScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MediaMainScreen(
    navController: NavHostController,
    mainUiState: MainUiState,
    mainUiEvent: (MainUiEvent) -> Unit
) {

    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(bottomNavController = bottomNavController)
        },
        content = {
            Box(
                modifier = Modifier
                    .background(color = MyMaterialTheme.appColor.backgroundColor)
                    .padding(bottom = it.calculateBottomPadding())
            ) {
                BottomNavGraph(
                    navController = navController,
                    bottomNavController = bottomNavController,
                    mainUiState = mainUiState,
                    mainUiEvent = mainUiEvent
                )
            }
        }
    )
}

@Composable
fun BottomBar(
    bottomNavController: NavHostController
) {
    val screens = listOf(
        BottomBarScreen.HOME_SCREEN,
        BottomBarScreen.POPULAR_MOVIE,
        BottomBarScreen.TV_SHOW,
        BottomBarScreen.WATCH_LIST
    )

    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MyMaterialTheme.appColor.bottomNavColor)
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDest = currentDestination,
                    bottomNavController = bottomNavController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDest: NavDestination?,
    bottomNavController: NavHostController
) {
    NavigationBarItem(
        selected = currentDest?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            bottomNavController.navigate(screen.route) {
                popUpTo(bottomNavController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = {
            if (currentDest?.hierarchy?.any { it.route == screen.route } == true) {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = "Navigation Icon",
                    tint = MyMaterialTheme.appColor.primaryColor
                )
            }
            else {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = "Navigation Icon",
                    tint = MyMaterialTheme.appColor.labelColor
                )
            }
        },
        label = {
            if (currentDest?.hierarchy?.any { it.route == screen.route } == true) {
                Text(
                    text = screen.title, color = MyMaterialTheme.appColor.primaryColor
                )
            }
            else {
                Text(
                    text = screen.title,
                    color = MyMaterialTheme.appColor.labelColor,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    )
}
