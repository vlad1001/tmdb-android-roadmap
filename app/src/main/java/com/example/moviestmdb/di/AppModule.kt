package com.example.moviestmdb.di

import com.example.moviestmdb.core.di.ApplicationScope
import com.example.moviestmdb.core.util.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideCoroutineDispatchers() = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
        immediate = Dispatchers.Main.immediate
    )

    @ApplicationScope
    @Singleton
    @Provides
    fun providesApplicationScope(
        dispatchers: AppCoroutineDispatchers
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatchers.computation)
}