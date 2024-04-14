package com.vanlam.moviebox.media_details.presentation.detail

import com.vanlam.moviebox.main.domain.model.Media

sealed class DetailScreenEvent {

    data class SetDataAndLoad(
        val media: Media
    ): DetailScreenEvent()

    data class HandleToWatchList(
        val media: Media
    ): DetailScreenEvent()
}
