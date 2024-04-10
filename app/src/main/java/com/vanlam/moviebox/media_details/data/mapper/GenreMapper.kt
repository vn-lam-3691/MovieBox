package com.vanlam.moviebox.media_details.data.mapper

import com.vanlam.moviebox.media_details.data.remote.response.GenreDto
import com.vanlam.moviebox.media_details.domain.model.Genre

fun GenreDto.toGenre(): Genre {
    return Genre(
        id = id,
        name = name
    )
}