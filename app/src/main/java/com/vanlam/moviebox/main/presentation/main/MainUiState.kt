package com.vanlam.moviebox.main.presentation.main

import com.vanlam.moviebox.main.domain.model.Media

data class MainUiState(
    val isLoading: Boolean = false,
    val loadMore: Boolean = false,
    val refresh: Boolean = false,

    val popularMoviePage: Int = 1,
    val tvShowPage: Int = 1,

    val trendingAllList: List<Media> = emptyList(),
    val discoverTvShowList: List<Media> = emptyList(),
    val topRatedMovieList: List<Media> = emptyList(),
    val popularMovieList: List<Media> = emptyList(),

    val mediaWatchList: List<Media> = emptyList()
)