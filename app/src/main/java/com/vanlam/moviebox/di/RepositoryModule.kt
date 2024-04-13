package com.vanlam.moviebox.di

import com.vanlam.moviebox.main.data.repository.MediaRepositoryImpl
import com.vanlam.moviebox.main.domain.repository.MediaRepository
import com.vanlam.moviebox.media_details.data.repository.GenreRepositoryImpl
import com.vanlam.moviebox.media_details.domain.repository.GenreRepository
import com.vanlam.moviebox.watch_list.data.repository.WatchListRepositoryImpl
import com.vanlam.moviebox.watch_list.domain.repository.WatchListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideMediaRepository(
        mediaRepositoryImpl: MediaRepositoryImpl
    ): MediaRepository

    @Binds
    @Singleton
    abstract fun provideGenreRepository(
        genreRepositoryImpl: GenreRepositoryImpl
    ): GenreRepository

    @Binds
    @Singleton
    abstract fun provideWatchListRepository(
        watchListRepositoryImpl: WatchListRepositoryImpl
    ): WatchListRepository
}