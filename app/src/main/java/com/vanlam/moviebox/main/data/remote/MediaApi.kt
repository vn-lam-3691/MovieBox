package com.vanlam.moviebox.main.data.remote

import com.vanlam.moviebox.main.data.remote.responde.MediaListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MediaApi {

    @GET("movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int
    ): MediaListDto

    @GET("discover/tv")
    suspend fun getTvShowList(
        @Query("page") page: Int
    ): MediaListDto

    @GET("trending/all/{time}")
    suspend fun getTrendingList(
        @Path("time") time: String,
        @Query("page") page: Int
    ): MediaListDto

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val BAST_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
        const val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMjA0MGNiNmQ2NzA4NmZjNDFjNWU1Y2ViZGM0NmQzYiIsInN1YiI6IjY1OTQxOWY4NmFhOGUwNjJkM2VhZjczOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.V4OA57MvCHcQxx0-9-nxjsOQ33IusUhpCoyR73QWo_o"
        const val API_KEY = "a2040cb6d67086fc41c5e5cebdc46d3b"
    }
}