package com.vanlam.moviebox.media_details.presentation

import com.vanlam.moviebox.main.domain.model.Media

sealed class DetailScreenEvent {

    data class SetDataAndLoad(
        val media: Media
    ): DetailScreenEvent()

    // Saved click event
}
