package com.vanlam.moviebox.media_details.data.mapper

import com.vanlam.moviebox.media_details.data.remote.response.VideoDto
import com.vanlam.moviebox.media_details.domain.model.Video

fun VideoDto.toVideo(): Video {
    return Video(
        id = id ?: "",
        iso_639_1 = iso_639_1 ?: "",
        iso_3166_1 = iso_3166_1 ?: "",
        key = key ?: "",
        name = name ?: "",
        official = official ?: false,
        published_at = published_at ?: "",
        site = site ?: "",
        size = size ?: 0,
        type = type ?: ""
    )
}
