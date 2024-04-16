package com.vanlam.moviebox.search_media.domain

import com.vanlam.moviebox.main.domain.model.Media
import com.vanlam.moviebox.main.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchList(
        query: String,
        page: Int
    ): Flow<Resource<List<Media>>>
}
