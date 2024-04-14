package com.vanlam.moviebox.di

import android.content.Context
import androidx.room.Room
import com.vanlam.moviebox.main.data.remote.MediaApi
import com.vanlam.moviebox.main.data.remote.MediaApi.Companion.ACCESS_TOKEN
import com.vanlam.moviebox.main.data.remote.MediaApi.Companion.BASE_URL
import com.vanlam.moviebox.media_details.data.remote.ExtraDetailApi
import com.vanlam.moviebox.media_details.data.remote.GenreApi
import com.vanlam.moviebox.watch_list.data.local.MediaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $ACCESS_TOKEN")
                .build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(interceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMediaApi(
        retrofit: Retrofit
    ): MediaApi {
        return retrofit.create(MediaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGenreApi(
        retrofit: Retrofit
    ): GenreApi {
        return retrofit.create(GenreApi::class.java)
    }

    @Provides
    @Singleton
    fun provideExtraDetailApi(
        retrofit: Retrofit
    ): ExtraDetailApi {
        return retrofit.create(ExtraDetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMediaDatabase(
        @ApplicationContext context: Context
    ): MediaDatabase {
        return Room.databaseBuilder(
            context,
            MediaDatabase::class.java,
            "media_db.db"
        ).build()
    }
}