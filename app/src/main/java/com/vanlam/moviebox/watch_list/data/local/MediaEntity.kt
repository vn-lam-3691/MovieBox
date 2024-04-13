package com.vanlam.moviebox.watch_list.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media_tb")
data class MediaEntity(
    @PrimaryKey
    val id: Int,

    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,

    val origin_country: String,
    val original_name: String,
    val first_air_date: String,
    val name: String
)