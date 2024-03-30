package com.vanlam.moviebox.di

import com.vanlam.moviebox.main.data.repository.MediaRepositoryImpl
import com.vanlam.moviebox.main.domain.repository.MediaRepository
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
}