package com.vanlam.moviebox.main.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.vanlam.moviebox.main.presentation.main.MainUiEvent
import com.vanlam.moviebox.main.presentation.main.MainUiState
import com.vanlam.moviebox.main.utils.Category
import com.vanlam.moviebox.ui.theme.MyMaterialTheme
import com.vanlam.moviebox.utils.Screen
import com.vanlam.moviebox.utils.ui_components.SearchBarNonFocus

@Composable
fun MediaHomeScreen(
    navController: NavHostController,
    bottomNavController: NavHostController,
    mainUiState: MainUiState,
    mainUiEvent: (MainUiEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MyMaterialTheme.appColor.backgroundColor)
    ) {
        SearchBarNonFocus(navController = navController)

        Spacer(modifier = Modifier.height(12.dp))

        MediaHomeSection(
            type = Category.TRENDING_ALL,
            navController = navController,
            mainUiState = mainUiState
        )

        Spacer(modifier = Modifier.height(28.dp))

        MediaHomeSection(
            type = Category.TV_DISCOVER,
            navController = navController,
            mainUiState = mainUiState
        )

        Spacer(modifier = Modifier.height(28.dp))

        MediaHomeSection(
            type = Category.TOP_RATED,
            navController = navController,
            mainUiState = mainUiState
        )

        Spacer(modifier = Modifier.height(28.dp))

        MediaHomeSection(
            type = Category.POPULAR,
            navController = navController,
            mainUiState = mainUiState
        )

        Spacer(modifier = Modifier.height(18.dp))
    }
}