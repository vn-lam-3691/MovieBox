package com.vanlam.moviebox.media_details.presentation

import com.vanlam.moviebox.main.domain.model.Media
import com.vanlam.moviebox.media_details.domain.model.Genre
import com.vanlam.moviebox.utils.Type

data class DetailsUiState(
    val isLoading: Boolean = false,
    val isSavedWatchList: Boolean = false,
    var typeMedia: String = Type.MOVIE,
    var mediaItem: Media? = null,
    val genres: List<String> = emptyList()
)