package com.vanlam.moviebox.watch_list.domain.repository

import com.vanlam.moviebox.main.domain.model.Media
import com.vanlam.moviebox.main.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WatchListRepository {

    suspend fun insertMedia(
        media: Media
    ): Flow<Resource<Boolean>>

    suspend fun getMediaById(
        id: Int
    ): Flow<Resource<Boolean>>

    suspend fun getAllWatchList(): Flow<Resource<List<Media>>>

    suspend fun deleteMedia(
        media: Media
    ): Flow<Resource<Int>>
}
