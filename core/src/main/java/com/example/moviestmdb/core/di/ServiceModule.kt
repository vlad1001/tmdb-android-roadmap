package com.example.moviestmdb.core.di

import com.example.moviestmdb.core.network.ConfigurationService
import com.example.moviestmdb.core.network.MovieService
import com.example.moviestmdb.core.network.TvShowService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideMovieService(retroFit: Retrofit): MovieService {
        return retroFit.create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideTvShowService(retroFit: Retrofit): TvShowService {
        return retroFit.create(TvShowService::class.java)
    }

    @Provides
    @Singleton
    fun provideConfigurationService(retroFit: Retrofit): ConfigurationService {
        return retroFit.create(ConfigurationService::class.java)
    }

}