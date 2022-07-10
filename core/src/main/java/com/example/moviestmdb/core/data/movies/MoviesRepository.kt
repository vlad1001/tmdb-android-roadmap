package com.example.moviestmdb.core.data.movies

import com.example.moviestmdb.Movie
import com.example.moviestmdb.core.data.movies.datasources.MoviesLocalDataSource
import com.example.moviestmdb.core.data.movies.datasources.MoviesRemoteDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remote: MoviesRemoteDataSource,
    private val local: MoviesLocalDataSource
) {

    suspend fun getPopularMovies(page: Int) =
        flow {
            emit(remote.getPopularMovies(page))
        }

    suspend fun savePopularMovies(page: Int, movies: List<Movie>) {
        local.popularStore.insert(page, movies)
    }

    fun observePopularMovies() = local.popularStore.observeEnteries()

    suspend fun getNowPlayingMovies(page: Int) =
        flow {
            emit(remote.getNowPlayingMovies(page))
        }

    fun observeNowPlayingMovies() = local.nowPlayingStore.observeEnteries()

    suspend fun getTopRatedMovies(page: Int) =
        flow {
            emit(remote.getTopRated(page))
        }

    fun observeTopRatedMovies() = local.topRatedStore.observeEnteries()

    suspend fun getUpcomingMovies(page: Int) =
        flow {
            emit(remote.getUpcoming(page))
        }

    fun observeUpcomingMovies() = local.upcomingStore.observeEnteries()
}