package com.example.moviestmdb.core.data.tv_shows

import com.example.moviestmdb.TvShow
import com.example.moviestmdb.core.data.tv_shows.data_sources.TvShowsLocalDataSource
import com.example.moviestmdb.core.data.tv_shows.data_sources.TvShowsRemoteDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowsRepository @Inject constructor(
    private val remote: TvShowsRemoteDataSource,
    private val local: TvShowsLocalDataSource,
) {
    /* popular tv shows */
    suspend fun getPopularTvShows(page: Int) =
        flow {
            emit(remote.getPopularTvShows(page))
        }

    fun savePopularTvShows(page: Int, tvShows: List<TvShow>) {
        local.popularTvShowsStore.insert(page, tvShows)
    }

    fun observePopularTvShows() = local.popularTvShowsStore.observeEntries()

    /* top rated tv shows */
    suspend fun getTopRatedTvShows(page: Int) =
        flow {
            emit(remote.getTopRatedTvShows(page))
        }

    fun saveTopRatedTvShows(page: Int, tvShows: List<TvShow>) {
        local.topRatedTvShowsStore.insert(page, tvShows)
    }

    fun observeTopRatedTvShows() = local.topRatedTvShowsStore.observeEntries()
}