package com.example.moviestmdb.core.data.movies

import com.example.moviestmdb.Cast
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CastsStore @Inject constructor() {

    private val _movieToCasts = MutableSharedFlow<Map<Int, List<Cast>>>(replay = 1).apply {
        tryEmit(emptyMap())
    }

    fun insert(movieId: Int, movies: List<Cast>) {
        val map = _movieToCasts.replayCache.first().toMutableMap()
        map[movieId] = movies
        _movieToCasts.tryEmit(map)
    }

    fun observeEnteries(): SharedFlow<Map<Int, List<Cast>>> =
        _movieToCasts.asSharedFlow()

    fun getCastsForMovie(movieId: Int) =
        _movieToCasts.replayCache.firstOrNull()?.let { it[movieId] }

}