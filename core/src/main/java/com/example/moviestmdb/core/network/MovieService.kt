package com.example.moviestmdb.core.network

import android.graphics.pdf.PdfDocument
import com.example.moviestmdb.MovieCreditsResponse
import com.example.moviestmdb.MovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/upcoming")
    fun getUpcoming(
        @Query("page") page: Int
    ) : Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRated(
        @Query("page") page: Int
    ) : Call<MovieResponse>

    @GET("movie/popular")
    fun getPopular(
        @Query("page") page: Int
    ) : Call<MovieResponse>

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("page") page: Int
    ) : Call<MovieResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") movieId: Int,
    ): Call<MovieCreditsResponse>

    @GET("movie/{movie_id}/recommendations")
    fun getMovieRecommendations(
        @Path("movie_id") movieId: Int,
    ): Call<MovieResponse>
}