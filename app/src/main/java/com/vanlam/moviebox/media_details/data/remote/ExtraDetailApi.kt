package com.vanlam.moviebox.media_details.data.remote

import com.vanlam.moviebox.media_details.data.remote.response.VideoListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ExtraDetailApi {

    @GET("{type}/{media_id}/videos")
    suspend fun getVideosList(
        @Path("type") type: String,
        @Path("media_id") movieId: Int
    ): VideoListDto
}
