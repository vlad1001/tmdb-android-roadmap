package com.example.moviestmdb

import com.google.gson.annotations.SerializedName

data class Genere(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
