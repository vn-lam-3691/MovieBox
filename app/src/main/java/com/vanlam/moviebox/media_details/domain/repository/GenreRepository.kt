package com.vanlam.moviebox.media_details.domain.repository

import com.vanlam.moviebox.main.utils.Resource
import com.vanlam.moviebox.media_details.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {

    suspend fun getAllGenres(
        type: String
    ): Flow<Resource<List<Genre>>>
}
