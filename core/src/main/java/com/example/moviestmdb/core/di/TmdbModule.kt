package com.example.moviestmdb.core.di

import com.example.moviestmdb.core.TmdbImageManager
import com.example.moviestmdb.util.TmdbImageUrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object TmdbModule {

    @Provides
    fun provideTmdbImageUrlProvider(tmdbManager: TmdbImageManager): TmdbImageUrlProvider {
        return tmdbManager.getLatestImageProvider()
    }
}