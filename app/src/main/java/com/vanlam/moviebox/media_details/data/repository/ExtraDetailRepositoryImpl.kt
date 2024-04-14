package com.vanlam.moviebox.media_details.data.repository

import com.vanlam.moviebox.main.utils.Resource
import com.vanlam.moviebox.media_details.data.mapper.toVideo
import com.vanlam.moviebox.media_details.data.remote.ExtraDetailApi
import com.vanlam.moviebox.media_details.domain.model.Video
import com.vanlam.moviebox.media_details.domain.repository.ExtraDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ExtraDetailRepositoryImpl @Inject constructor(
    private val extraDetailApi: ExtraDetailApi
): ExtraDetailRepository {
    override suspend fun getVideosList(
        type: String,
        mediaId: Int
    ): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading(true))

            val videoList = try {
                extraDetailApi.getVideosList(type, mediaId)
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

            if (videoList.results.isNotEmpty()) {
                val videos = videoList.results.filter {
                    it.type == "Trailer" && it.site == "YouTube" || it.name?.contains("Official")!!
                }.map {
                    it.toVideo()
                }

                emit(Resource.Success(if (videos.isNotEmpty()) videos[videos.size - 1].key else "-1"))
            }
            else {
                emit(Resource.Success("-1"))
            }

            emit(Resource.Loading(false))
        }
    }
}
