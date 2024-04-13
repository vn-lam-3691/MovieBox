package com.vanlam.moviebox.watch_list.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vanlam.moviebox.R
import com.vanlam.moviebox.main.presentation.main.MainUiState
import com.vanlam.moviebox.ui.theme.MyMaterialTheme
import com.vanlam.moviebox.utils.ui_components.MediaItem

@Composable
fun MediaWatchListScreen(
    navController: NavHostController,
    mainUiState: MainUiState
) {
    val watchList = mainUiState.mediaWatchList

    if (watchList.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MyMaterialTheme.appColor.backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_image),
                contentDescription = "Empty"
            )

            Text(
                text = "Your watch list is empty!",
                style = MaterialTheme.typography.titleLarge,
                color = MyMaterialTheme.appColor.textColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Explore more and add to watch list", style = MaterialTheme.typography.bodyMedium, color = MyMaterialTheme.appColor.labelColor)
        }
    }
    else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 6.dp),
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxSize()
        ) {

            item(
                span = { GridItemSpan(this.maxLineSpan) }
            ) {
                Column {
                    Text(
                        text = "Watch List",
                        style = MaterialTheme.typography.titleLarge,
                        color = MyMaterialTheme.appColor.textColor,
                        modifier = Modifier.padding(start = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                }
            }

            items(watchList.size) { index ->
                MediaItem(
                    media = watchList[index],
                    navController = navController
                )
            }
        }
    }
}
