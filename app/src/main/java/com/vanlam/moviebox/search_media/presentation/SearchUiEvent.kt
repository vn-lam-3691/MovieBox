package com.vanlam.moviebox.search_media.presentation

sealed class SearchUiEvent {
    data class OnSearchQueryChanged(val query: String) : SearchUiEvent()
    object LoadMore: SearchUiEvent()
}
