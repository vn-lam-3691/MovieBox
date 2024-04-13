package com.vanlam.moviebox.watch_list.data.repository

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import com.vanlam.moviebox.main.domain.model.Media
import com.vanlam.moviebox.main.utils.Resource
import com.vanlam.moviebox.watch_list.data.local.MediaDatabase
import com.vanlam.moviebox.watch_list.data.mapper.toMedia
import com.vanlam.moviebox.watch_list.data.mapper.toMediaEntity
import com.vanlam.moviebox.watch_list.domain.repository.WatchListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class WatchListRepositoryImpl @Inject constructor(
    private val mediaDatabase: MediaDatabase
): WatchListRepository {
    override suspend fun insertMedia(media: Media): Flow<Resource<Boolean>> {
        val mediaEntity = media.toMediaEntity()

        return flow {
            emit(Resource.Loading(true))

            val response = try {
                mediaDatabase.mediaDao.insertMedia(mediaEntity)
            } catch (e: SQLiteConstraintException) {
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

            if (response == media.id.toLong()) {
                emit(Resource.Success(true))
            } else {
                emit(Resource.Success(false))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMediaById(id: Int): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            val mediaResult = try {
                mediaDatabase.mediaDao.getMediaById(id)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
                return@flow
            }

            if (mediaResult?.toMedia()?.id == id) {
                emit(Resource.Success(true))
            } else {
                emit(Resource.Success(false))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getAllWatchList(): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val mediaEntityList = try {
                mediaDatabase.mediaDao.getAllWatchList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
                return@flow
            }

            val watchList = mediaEntityList.map { entity ->
                entity.toMedia()
            }

            emit(Resource.Success(watchList))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun deleteMedia(media: Media): Flow<Resource<Int>> {
        return flow {
            emit(Resource.Loading(true))

            val mediaEntity = media.toMediaEntity()
            val response = try {
                mediaDatabase.mediaDao.deleteMedia(mediaEntity)
            } catch (e: SQLiteException) {
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

            if (response == media.id) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Success(-1))
            }

            emit(Resource.Loading(false))
        }
    }
}
