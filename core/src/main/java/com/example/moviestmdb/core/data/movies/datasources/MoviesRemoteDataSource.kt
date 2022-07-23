package com.example.moviestmdb.core.data.movies.datasources

import com.example.moviestmdb.MovieCreditsResponse
import com.example.moviestmdb.MovieResponse
import com.example.moviestmdb.core.network.MovieService
import com.example.moviestmdb.core.extensions.executeWithRetry
import com.example.moviestmdb.core.extensions.toResult
import com.example.moviestmdb.core.result.Result
import com.example.moviestmdb.core.util.safeApiCall
import javax.inject.Inject


class MoviesRemoteDataSource @Inject constructor(
    private val moviesService: MovieService
) {

    suspend fun getNowPlayingMovies(page: Int): Result<MovieResponse> {
        return safeApiCall {
            moviesService
                .getNowPlaying(page)
                .executeWithRetry()
                .toResult()
        }
    }

    suspend fun getPopularMovies(page: Int): Result<MovieResponse> {
        return safeApiCall {
            moviesService
                .getPopular(page)
                .executeWithRetry()
                .toResult()
        }
    }

    suspend fun getUpcoming(page: Int): Result<MovieResponse> {
        return safeApiCall {
            moviesService
                .getUpcoming(page)
                .executeWithRetry()
                .toResult()
        }
    }

    suspend fun getTopRated(page: Int): Result<MovieResponse> {
        return safeApiCall {
            moviesService
                .getTopRated(page)
                .executeWithRetry()
                .toResult()
        }
    }

    suspend fun getMovieCredits(movieId: Int): Result<MovieCreditsResponse> {
        return safeApiCall {
            moviesService.getMovieCredits(movieId)
                .executeWithRetry()
                .toResult()
        }
    }

    suspend fun getMovieRecommendations(movieId: Int): Result<MovieResponse> {
        return safeApiCall {
            moviesService.getMovieRecommendations(movieId)
                .executeWithRetry()
                .toResult()
        }
    }

}