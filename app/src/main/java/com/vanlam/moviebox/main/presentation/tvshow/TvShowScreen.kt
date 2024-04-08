package com.vanlam.moviebox.main.presentation.tvshow

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vanlam.moviebox.main.presentation.main.MainUiEvent
import com.vanlam.moviebox.main.presentation.main.MainUiState
import com.vanlam.moviebox.main.utils.Category
import com.vanlam.moviebox.ui.theme.MyMaterialTheme
import com.vanlam.moviebox.utils.ui_components.MediaItem
import com.vanlam.moviebox.utils.ui_components.SearchBarNonFocus

@Composable
fun TvShowScreen(
    navController: NavHostController,
    mainUiState: MainUiState,
    mainUiEvent: (MainUiEvent) -> Unit
) {
    val tvShowList = mainUiState.discoverTvShowList

    if (tvShowList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 6.dp),
            modifier = Modifier.fillMaxSize()
        ) {

            item(
                span = { GridItemSpan(this.maxLineSpan) }
            ) {
                Column {
                    SearchBarNonFocus(navController = navController)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "TV Shows",
                        style = MaterialTheme.typography.titleLarge,
                        color = MyMaterialTheme.appColor.textColor,
                        modifier = Modifier.padding(start = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                }
            }

            items(tvShowList.size) { index ->
                MediaItem(
                    media = tvShowList[index],
                    navController = navController
                )

                if (index >= tvShowList.size - 1) {
                    mainUiEvent(MainUiEvent.LoadMore(Category.TV_SHOW))
                }
            }
        }
    }
}
