package com.vanlam.moviebox.search_media.presentation

import com.vanlam.moviebox.main.domain.model.Media

data class SearchUiState(
    val isLoading: Boolean = true,
    var searchQuery: String = "",

    val searchPage: Int = 1,

    val searchList: List<Media> = emptyList()
)
