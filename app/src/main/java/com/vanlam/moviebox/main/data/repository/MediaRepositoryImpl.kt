package com.vanlam.moviebox.main.data.repository

import com.vanlam.moviebox.main.data.mapper.toMedia
import com.vanlam.moviebox.main.data.remote.MediaApi
import com.vanlam.moviebox.main.domain.model.Media
import com.vanlam.moviebox.main.domain.repository.MediaRepository
import com.vanlam.moviebox.main.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaApi: MediaApi
): MediaRepository {
    override suspend fun getMovieList(category: String, page: Int): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val remoteMovieList = try {
                mediaApi.getMovieList(category, page).results
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
                return@flow
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
                return@flow
            }

            remoteMovieList.let {
                val movieList = it.map { mediaDto ->
                    mediaDto.toMedia()
                }

                emit(Resource.Success(movieList))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getTvShowList(page: Int): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val remoteTvShowList = try {
                mediaApi.getTvShowList(page)
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

            val tvShowList = remoteTvShowList.results.map { mediaDto ->
                mediaDto.toMedia()
            }
            emit(Resource.Success(tvShowList))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getTrendingMediaList(
        time: String,
        page: Int
    ): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val remoteTrendingMediaList = try {
                mediaApi.getTrendingList(time, page)
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

            val trendingList = remoteTrendingMediaList.results.map {
                it.toMedia()
            }
            emit(Resource.Success(trendingList))
            emit(Resource.Loading(false))
        }
    }
}