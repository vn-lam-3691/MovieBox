package com.vanlam.moviebox.media_details.domain.repository

import com.vanlam.moviebox.main.utils.Resource
import com.vanlam.moviebox.media_details.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface ExtraDetailRepository {
    suspend fun getVideosList(
        type: String,
        mediaId: Int
    ): Flow<Resource<String>>
}
