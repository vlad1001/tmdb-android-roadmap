package com.example.moviestmdb.core.network

import com.example.moviestmdb.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowService {

    @GET("tv/popular")
    fun getPopularTvShows(
        @Query("page") page: Int
    ): Call<TvShowResponse>

    @GET("tv/top_rated")
    fun getTopRatedTvShows(
        @Query("page") page: Int
    ): Call<TvShowResponse>
}