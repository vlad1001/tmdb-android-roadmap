package com.example.moviestmdb

import com.google.gson.annotations.SerializedName

data class MovieResponse (
    @SerializedName("page")
    val page : Int = 0,
    @SerializedName("results")
    val movieList: List<Movie> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)