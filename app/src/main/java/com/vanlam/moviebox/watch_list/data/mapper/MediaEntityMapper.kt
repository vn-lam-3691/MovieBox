package com.vanlam.moviebox.watch_list.data.mapper

import com.vanlam.moviebox.main.domain.model.Media
import com.vanlam.moviebox.watch_list.data.local.MediaEntity

fun MediaEntity.toMedia(): Media {
    return Media(
        id = id,
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        genre_ids = try {
            genre_ids.split(",").map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        },
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

        origin_country = try {
            origin_country.split(",")
        } catch (e: Exception) {
            listOf("en")
        },
        original_name = original_name ?: "",
        first_air_date = first_air_date ?: "",
        name = name ?: ""
    )
}

fun Media.toMediaEntity(): MediaEntity {
    return MediaEntity(
        id = id,
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        genre_ids = try {
            genre_ids.joinToString(",")
        } catch (e: Exception) {
            "-1,-2"
        },
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

        origin_country = try {
            origin_country.joinToString(",")
        } catch (e: Exception) {
            "en"
        },
        original_name = original_name ?: "",
        first_air_date = first_air_date ?: "",
        name = name ?: ""
    )
}
