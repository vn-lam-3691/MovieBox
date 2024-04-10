package com.vanlam.moviebox.media_details.data.repository

import com.vanlam.moviebox.main.utils.Resource
import com.vanlam.moviebox.media_details.data.mapper.toGenre
import com.vanlam.moviebox.media_details.data.remote.GenreApi
import com.vanlam.moviebox.media_details.domain.model.Genre
import com.vanlam.moviebox.media_details.domain.repository.GenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreApi: GenreApi
): GenreRepository {

    override suspend fun getAllGenres(type: String): Flow<Resource<List<Genre>>> {
        return flow {
            emit(Resource.Loading(true))

            val genreDtoList = try {
                genreApi.getAllGenre(type)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
                return@flow
            }

            genreDtoList.let {
                val genreList = genreDtoList.genres.map {
                    it.toGenre()
                }
                emit(Resource.Success(genreList))
                emit(Resource.Loading(false))
            }
        }
    }
}
