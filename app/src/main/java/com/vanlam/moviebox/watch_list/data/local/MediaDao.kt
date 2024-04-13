package com.vanlam.moviebox.watch_list.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(mediaEntity: MediaEntity): Long

    @Query("SELECT * FROM media_tb WHERE id=:id")
    suspend fun getMediaById(id: Int): MediaEntity?

    @Query("SELECT * FROM media_tb")
    suspend fun getAllWatchList(): List<MediaEntity>

    @Delete
    suspend fun deleteMedia(mediaEntity: MediaEntity): Int
}
