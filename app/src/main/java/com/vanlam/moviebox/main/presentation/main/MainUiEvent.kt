package com.vanlam.moviebox.main.presentation.main

sealed interface MainUiEvent {
    data class LoadMore(val category: String): MainUiEvent
}