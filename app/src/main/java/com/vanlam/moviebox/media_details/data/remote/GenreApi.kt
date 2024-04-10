package com.vanlam.moviebox.media_details.data.remote

import com.vanlam.moviebox.media_details.data.remote.response.GenreListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GenreApi {

    @GET("genre/{type}/list")
    suspend fun getAllGenre(
        @Path("type") type: String
    ): GenreListDto
}
