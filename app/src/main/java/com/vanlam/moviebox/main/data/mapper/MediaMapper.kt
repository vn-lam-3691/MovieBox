package com.vanlam.moviebox.main.data.mapper

import com.vanlam.moviebox.main.data.remote.responde.MediaDto
import com.vanlam.moviebox.main.domain.model.Media

fun MediaDto.toMedia(): Media {
    return Media(
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        genre_ids = genre_ids ?: emptyList(),
        id = id,
        original_language = original_language ?: "",
        original_title = original_title ?: "",
        overview = overview ?: "",
        popularity = popularity ?: 0.0,
        poster_path = poster_path ?: "",
        release_date = release_date ?: "",
        title = title ?: "",
        video = video ?: false,
        vote_average = vote_average ?: 0.0,
        vote_count = vote_count ?: 0,

        origin_country = origin_country ?: emptyList(),
        original_name = original_name ?: "",
        first_air_date = first_air_date ?: "",
        name = name ?: ""
    )
}

