package com.vanlam.moviebox.search_media.data.repository

import com.vanlam.moviebox.main.data.mapper.toMedia
import com.vanlam.moviebox.main.domain.model.Media
import com.vanlam.moviebox.main.utils.Resource
import com.vanlam.moviebox.search_media.data.remote.MediaSearchApi
import com.vanlam.moviebox.search_media.domain.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: MediaSearchApi
): SearchRepository {
    override suspend fun getSearchList(query: String, page: Int): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val searchResults = try {
                searchApi.getSearchList(
                    query, page
                ).results.map {
                    it.toMedia()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message))
                return@flow
            }

            emit(Resource.Success(searchResults))
            emit(Resource.Loading(false))
        }
    }
}
