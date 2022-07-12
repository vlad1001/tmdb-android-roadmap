package com.example.moviestmdb.core.network

import com.example.moviestmdb.util.Configuration
import retrofit2.Call
import retrofit2.http.GET

interface ConfigurationService {

    @GET("configuration")
    fun getConfiguration(): Call<Configuration>
}