package com.vanlam.moviebox.main.domain.repository

import com.vanlam.moviebox.main.domain.model.Media
import com.vanlam.moviebox.main.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    suspend fun getMovieList(
        category: String,
        page: Int
    ): Flow<Resource<List<Media>>>

    suspend fun getTvShowList(
        page: Int
    ): Flow<Resource<List<Media>>>

    suspend fun getTrendingMediaList(
        time: String,
        page: Int
    ): Flow<Resource<List<Media>>>
}
