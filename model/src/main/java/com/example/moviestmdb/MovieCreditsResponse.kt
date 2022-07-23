package com.example.moviestmdb

import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val casts: List<Cast>,
    @SerializedName("crew")
    val crews: List<Cast>,
)