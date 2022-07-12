package com.example.moviestmdb

import com.google.gson.annotations.SerializedName

data class Movie (
    @SerializedName("poster_path")
    val posterPath : String? = null,
    @SerializedName("adult")
    val isAdult : Boolean = false,
    @SerializedName("overview")
    val overView : String = "",
    @SerializedName("release_date")
    val releaseDate : String? = null,
    @SerializedName("genre_ids")
    val genreList: List<Int> = listOf(),
    @SerializedName("id")
    val id : Int,
    @SerializedName("original_title")
    val originalTitle : String? = null,
    @SerializedName("original_language")
    val originalLanguage : String? = null,
    @SerializedName("title")
    val title : String? = null,
    @SerializedName("backdrop_path")
    val backdropPath : String? = null,
    @SerializedName("popularity")
    val popularity : Double? = null,
    @SerializedName("vote_count")
    val voteCount : Int? = null,
    @SerializedName("video")
    val isVideo : Boolean = false,
    @SerializedName("vote_average")
    val voteAverage : Double? = null,
)