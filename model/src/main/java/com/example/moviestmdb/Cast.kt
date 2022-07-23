package com.example.moviestmdb

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("adult")
    val adult: Boolean? = null,
    @SerializedName("gender")
    val gender: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null,
    @SerializedName("character")
    val character: String? = null,
    @SerializedName("credit_id")
    val creditId: String? = null,

    )