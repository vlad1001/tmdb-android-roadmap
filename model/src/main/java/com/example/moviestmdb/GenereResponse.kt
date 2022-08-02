package com.example.moviestmdb

import com.google.gson.annotations.SerializedName

data class GenereResponse(
    @SerializedName("genres")
    val generes: List<Genere>
) {

}