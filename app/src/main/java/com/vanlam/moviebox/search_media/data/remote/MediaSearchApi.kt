package com.vanlam.moviebox.search_media.data.remote

import com.vanlam.moviebox.main.data.remote.responde.MediaListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaSearchApi {
    @GET("search/multi")
    suspend fun getSearchList(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("language") language: String = "en-US"
    ): MediaListDto
}
