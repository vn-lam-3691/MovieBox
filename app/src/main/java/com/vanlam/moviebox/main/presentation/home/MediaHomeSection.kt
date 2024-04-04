package com.vanlam.moviebox.main.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vanlam.moviebox.main.presentation.main.MainUiState
import com.vanlam.moviebox.main.utils.Category
import com.vanlam.moviebox.utils.ui_components.MediaHomeItem

@Composable
fun MediaHomeSection(
    type: String,
    navController: NavHostController,
    mainUiState: MainUiState
) {

    val title = when (type) {
        Category.TRENDING_ALL -> {
            "Trending Now"
        }

        Category.TV_DISCOVER -> {
            "Discover TV Shows"
        }

        Category.TOP_RATED -> {
            "Top Rated"
        }

        else -> {
            "Popular Movie"
        }
    }

    val mediaList = when (type) {
        Category.TRENDING_ALL -> {
            mainUiState.trendingAllList.take(10)
        }

        Category.TV_DISCOVER -> {
            mainUiState.discoverTvShowList.take(10)
        }

        Category.TOP_RATED -> {
            mainUiState.topRatedMovieList.take(10)
        }

        else -> {
            mainUiState.popularMovieList.take(10)
        }
    }

    Column {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.inverseOnSurface,
            modifier = Modifier
                .padding(start = 24.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(mediaList) { media ->
                MediaHomeItem(
                    media = media,
                    navController = navController,
                    modifier = Modifier
                        .size(width = 120.dp, height = 160.dp)
                )
            }
        }
    }
}