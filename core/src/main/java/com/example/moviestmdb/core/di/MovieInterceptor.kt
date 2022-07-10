package com.example.moviestmdb.core.di

import com.example.moviestmdb.core.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response

class MovieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val updatedUrl = request.url.
            newBuilder()
            .addQueryParameter("api_key", Constants.API_KEY)
            .build()

        val updatedRequest = request.newBuilder().url(updatedUrl).build()

        return chain.proceed(updatedRequest)
    }
}