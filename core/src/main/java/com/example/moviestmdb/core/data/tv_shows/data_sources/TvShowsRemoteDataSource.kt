package com.example.moviestmdb.core.data.tv_shows.data_sources

import com.example.moviestmdb.TvShowResponse
import com.example.moviestmdb.core.extensions.executeWithRetry
import com.example.moviestmdb.core.extensions.toResult
import com.example.moviestmdb.core.network.TvShowService
import com.example.moviestmdb.core.result.Result
import com.example.moviestmdb.core.util.safeApiCall
import javax.inject.Inject

class TvShowsRemoteDataSource @Inject constructor(
    private val tvShowsApiService: TvShowService
){

    suspend fun getTopRatedTvShows(page: Int): Result<TvShowResponse> {
        return safeApiCall {
            tvShowsApiService
                .getTopRatedTvShows(page)
                .executeWithRetry()
                .toResult()
        }
    }

    suspend fun getPopularTvShows(page: Int): Result<TvShowResponse> {
        return safeApiCall {
            tvShowsApiService
                .getPopularTvShows(page)
                .executeWithRetry()
                .toResult()
        }
    }
}