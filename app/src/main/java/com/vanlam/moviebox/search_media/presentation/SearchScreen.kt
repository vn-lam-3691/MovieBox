package com.vanlam.moviebox.search_media.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vanlam.moviebox.R
import com.vanlam.moviebox.main.presentation.main.MainUiEvent
import com.vanlam.moviebox.main.utils.Category
import com.vanlam.moviebox.ui.theme.MyMaterialTheme
import com.vanlam.moviebox.utils.ui_components.MediaItem
import com.vanlam.moviebox.utils.ui_components.SearchBar
import com.vanlam.moviebox.utils.ui_components.SearchBarNonFocus

@Composable
fun SearchScreen(
    navController: NavHostController
) {
    val searchViewModel = hiltViewModel<SearchViewModel>()
    val searchUiState = searchViewModel.searchState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MyMaterialTheme.appColor.backgroundColor)
    ) {
        SearchBar(searchUiState) {
            searchViewModel.onEvent(SearchUiEvent.OnSearchQueryChanged(it))
        }

        if (searchUiState.searchList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.search_empty),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )

                Text(
                    text = "No results found",
                    style = MaterialTheme.typography.titleLarge,
                    color = MyMaterialTheme.appColor.textColor
                )
            }
        } else {
            Spacer(modifier = Modifier.height(14.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 6.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                items(searchUiState.searchList.size) { index ->
                    MediaItem(
                        media = searchUiState.searchList[index],
                        navController = navController
                    )

                    if (index >= searchUiState.searchList.size - 1) {
                        searchViewModel.onEvent(SearchUiEvent.LoadMore)
                    }
                }
            }
        }
    }
}
