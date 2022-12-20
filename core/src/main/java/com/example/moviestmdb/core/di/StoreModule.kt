package com.example.moviestmdb.core.di

import com.example.moviestmdb.core.data.movies.MoviesStore
import com.example.moviestmdb.core.data.tv_shows.TvShowsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StoreModule {

    @Singleton
    @Provides
    @Popular
    fun providePopularStore(): MoviesStore = MoviesStore()

    @Singleton
    @Provides
    @NowPlaying
    fun provideNowPlayingStore(): MoviesStore = MoviesStore()

    @Singleton
    @Provides
    @Upcoming
    fun provideUpcomingStore(): MoviesStore = MoviesStore()

    @Singleton
    @Provides
    @TopRated
    fun provideTopRatedStore(): MoviesStore = MoviesStore()

    @Singleton
    @Provides
    @TopRatedTvShows
    fun provideTopRatedTvShowStore(): TvShowsStore = TvShowsStore()

    @Singleton
    @Provides
    @PopularTvShows
    fun providePopularTvShowStore(): TvShowsStore = TvShowsStore()
}